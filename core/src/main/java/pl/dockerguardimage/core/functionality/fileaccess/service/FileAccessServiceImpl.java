package pl.dockerguardimage.core.functionality.fileaccess.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.core.functionality.fileaccess.dto.FileAccessDTO;
import pl.dockerguardimage.core.functionality.fileaccess.dto.FileAccessRemoveDTO;
import pl.dockerguardimage.core.functionality.notification.service.NotificationService;
import pl.dockerguardimage.data.functionality.accesstype.domain.AccessType;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccessId;
import pl.dockerguardimage.data.functionality.fileaccess.service.FileAccessCudService;
import pl.dockerguardimage.data.functionality.fileaccess.service.FileAccessQueryService;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanQueryService;
import pl.dockerguardimage.data.functionality.notification.service.AccessTypeCudService;
import pl.dockerguardimage.data.functionality.user.domain.User;
import pl.dockerguardimage.data.functionality.user.service.UserQueryService;
import pl.dockerguardimage.security.functionality.user.context.UserContextHolder;

import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class FileAccessServiceImpl implements FileAccessService {

    private final ImageScanQueryService imageScanQueryService;
    private final UserQueryService userQueryService;
    private final NotificationService notificationService;
    private final FileAccessCudService fileAccessCudService;
    private final FileAccessQueryService fileAccessQueryService;
    private final AccessTypeCudService accessTypeCudService;

    @Override
    public void process(FileAccessDTO dto) {
        var imageScan = imageScanQueryService.getByIdWithFileAccess(dto.imageScanId());
        var user = userQueryService.getById(dto.userId());
        var fileAccesses = imageScan.getFileAccesses()
                .stream()
                .filter(fa -> fa.getUser().getId().equals(dto.userId()))
                .collect(Collectors.toSet());
        if (!fileAccesses.isEmpty()) {
            changedAccessToFile(dto, imageScan, user, fileAccesses);
            return;
        }

        addNewAccessToFile(dto, imageScan, user);
    }

    @Override
    public void delete(FileAccessRemoveDTO dto) {
        var fileAccess = fileAccessQueryService.getById(new FileAccessId(dto.userId(), dto.imageScanId()));
        var user = userQueryService.getById(UserContextHolder.getAuthenticatedUser().id());
        var givenTo = fileAccess.getUser();
        notificationService.removedAccessType(user, givenTo, fileAccess);
        var accessType = fileAccess.getAccessType();
        fileAccess.setAccessType(null);
        accessType.setFileAccess(null);
        accessTypeCudService.delete(accessType);
        fileAccessCudService.delete(fileAccess);
    }

    private void addNewAccessToFile(FileAccessDTO dto, ImageScan imageScan, User user) {
        var fileAccess = new FileAccess();
        var accessType = new AccessType();
        accessType.setName(dto.permission());
        accessType.setDescription(dto.permission().name());

        var createAccessType = accessTypeCudService.create(accessType);
        fileAccess.setAccessType(createAccessType);
        imageScan.addFileAccess(fileAccess);
        user.addFileAccess(fileAccess);
        fileAccess.setId(new FileAccessId(user.getId(), imageScan.getId()));
        fileAccessCudService.create(fileAccess);
        notificationService.addAccessType(imageScan.getAuthor(), user, fileAccess);
    }

    private void changedAccessToFile(FileAccessDTO dto, ImageScan imageScan, User user, Set<FileAccess> fileAccesses) {
        var fileAccess = fileAccesses.iterator().next();
        var permission = fileAccess.getAccessType();
        if (permission.getName().equals(dto.permission())) {
            return;
        }

        permission.setName(dto.permission());
        accessTypeCudService.update(permission);
        notificationService.changedAccessType(imageScan.getAuthor(), user, fileAccess);
    }


}
