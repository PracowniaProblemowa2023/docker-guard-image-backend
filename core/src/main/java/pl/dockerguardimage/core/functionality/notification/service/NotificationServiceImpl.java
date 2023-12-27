package pl.dockerguardimage.core.functionality.notification.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.accesstype.domain.AccessTypePermission;
import pl.dockerguardimage.data.functionality.accesstype.service.NotificationCudService;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;
import pl.dockerguardimage.data.functionality.notification.domain.Notification;
import pl.dockerguardimage.data.functionality.notification.domain.NotificationType;
import pl.dockerguardimage.data.functionality.user.domain.User;

@Service
@Transactional
@AllArgsConstructor
class NotificationServiceImpl implements NotificationService {

    private final NotificationCudService notificationCudService;

    @Override
    public Notification addAccessType(User author, User givenTo, FileAccess fileAccess) {
        var notification = new Notification();
        var isRead = fileAccess.getAccessType().getName().equals(AccessTypePermission.READ);
        notification.setType(isRead ? NotificationType.FILE_ACCESS_ADD_READ : NotificationType.FILE_ACCESS_ADD_WRITE);
        notification.setMessage(isRead ? "common.addAccessRead" : "common.addAccessWrite");
        notification.setUsername(author.getUsername());
        notification.setAdditionalInformation(fileAccess.getImageScan().getImageName());
        notification.setElementId(fileAccess.getImageScan().getId());
        notification.addUser(givenTo);
        return notificationCudService.create(notification);
    }

    @Override
    public Notification removedAccessType(User author, User givenTo, FileAccess fileAccess) {
        var notification = new Notification();
        notification.setType(NotificationType.FILE_ACCESS_REMOVE);
        notification.setMessage("common.removeAccess");
        notification.setUsername(author.getUsername());
        notification.setAdditionalInformation(fileAccess.getImageScan().getImageName());
        notification.setElementId(fileAccess.getImageScan().getId());
        notification.addUser(givenTo);
        return notificationCudService.create(notification);
    }

    @Override
    public Notification changedAccessType(User author, User givenTo, FileAccess fileAccess) {
        var notification = new Notification();
        var isRead = fileAccess.getAccessType().getName().equals(AccessTypePermission.READ);
        notification.setType(isRead ? NotificationType.FILE_ACCESS_UPDATE_READ : NotificationType.FILE_ACCESS_UPDATE_WRITE);
        notification.setMessage(isRead ? "common.moveAccessRead" : "common.moveAccessWrite");
        notification.setUsername(author.getUsername());
        notification.setAdditionalInformation(fileAccess.getImageScan().getImageName());
        notification.setElementId(fileAccess.getImageScan().getId());
        notification.addUser(givenTo);
        return notificationCudService.create(notification);
    }

}
