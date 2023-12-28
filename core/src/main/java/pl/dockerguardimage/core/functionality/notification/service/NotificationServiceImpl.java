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
        var type = fileAccess.getAccessType().getName().equals(AccessTypePermission.READ)
                ? NotificationType.FILE_ACCESS_ADD_READ
                : NotificationType.FILE_ACCESS_ADD_WRITE;
        return createNow(author, givenTo, fileAccess, notification, type);
    }

    @Override
    public Notification removedAccessType(User author, User givenTo, FileAccess fileAccess) {
        var notification = new Notification();
        var type = NotificationType.FILE_ACCESS_REMOVE;
        return createNow(author, givenTo, fileAccess, notification, type);
    }

    @Override
    public Notification changedAccessType(User author, User givenTo, FileAccess fileAccess) {
        var notification = new Notification();
        var type = fileAccess.getAccessType().getName().equals(AccessTypePermission.READ)
                ? NotificationType.FILE_ACCESS_UPDATE_READ
                : NotificationType.FILE_ACCESS_UPDATE_WRITE;
        return createNow(author, givenTo, fileAccess, notification, type);
    }

    private Notification createNow(User author, User givenTo, FileAccess fileAccess, Notification notification, NotificationType type) {
        notification.setType(type);
        notification.setMessage(type.getMessage());
        notification.setUsername(author.getFullName());
        notification.setAdditionalInformation(fileAccess.getImageScan().getImageName());
        notification.setElementId(fileAccess.getImageScan().getId());
        notification.addUser(givenTo);
        return notificationCudService.create(notification);
    }

}
