package pl.dockerguardimage.core.functionality.notification.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.accesstype.domain.AccessTypePermission;
import pl.dockerguardimage.data.functionality.accesstype.service.NotificationCudService;
import pl.dockerguardimage.data.functionality.comment.domain.Comment;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;
import pl.dockerguardimage.data.functionality.notification.domain.Notification;
import pl.dockerguardimage.data.functionality.notification.domain.NotificationType;
import pl.dockerguardimage.data.functionality.user.domain.User;

import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
class NotificationServiceImpl implements NotificationService {

    private final NotificationCudService notificationCudService;

    @Override
    public Notification addAccessType(User author, User givenTo, FileAccess fileAccess) {
        var type = fileAccess.getAccessType().getName().equals(AccessTypePermission.READ)
                ? NotificationType.FILE_ACCESS_ADD_READ
                : NotificationType.FILE_ACCESS_ADD_WRITE;
        return createNow(author, givenTo, fileAccess, type);
    }

    @Override
    public Notification removedAccessType(User author, User givenTo, FileAccess fileAccess) {
        var type = NotificationType.FILE_ACCESS_REMOVE;
        return createNow(author, givenTo, fileAccess, type);
    }

    @Override
    public Notification changedAccessType(User author, User givenTo, FileAccess fileAccess) {
        var type = fileAccess.getAccessType().getName().equals(AccessTypePermission.READ)
                ? NotificationType.FILE_ACCESS_UPDATE_READ
                : NotificationType.FILE_ACCESS_UPDATE_WRITE;
        return createNow(author, givenTo, fileAccess, type);
    }

    @Override
    public Notification addComment(Comment comment) {
        var users = comment.getImageScan().getFileAccesses()
                .stream()
                .map(FileAccess::getUser)
                .collect(Collectors.toSet());
        users.add(comment.getImageScan().getAuthor());
        users.remove(comment.getAuthor());
        var notification = new Notification();
        notification.setType(NotificationType.COMMENT);
        notification.setMessage(NotificationType.COMMENT.getMessage());
        notification.setUsername(comment.getAuthor().getFullName());
        notification.setAdditionalInformation(comment.getImageScan().getImageName());
        notification.setElementId(comment.getImageScan().getId());
        users.forEach(notification::addUser);
        return notificationCudService.create(notification);
    }

    private Notification createNow(User author, User givenTo, FileAccess fileAccess, NotificationType type) {
        var notification = new Notification();
        notification.setType(type);
        notification.setMessage(type.getMessage());
        notification.setUsername(author.getFullName());
        notification.setAdditionalInformation(fileAccess.getImageScan().getImageName());
        notification.setElementId(fileAccess.getImageScan().getId());
        notification.addUser(givenTo);
        return notificationCudService.create(notification);
    }

}
