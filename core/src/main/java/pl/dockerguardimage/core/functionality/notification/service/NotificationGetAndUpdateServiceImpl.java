package pl.dockerguardimage.core.functionality.notification.service;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.core.functionality.notification.dto.NotificationDTO;
import pl.dockerguardimage.data.functionality.accesstype.service.NotificationCudService;
import pl.dockerguardimage.data.functionality.accesstype.service.NotificationQueryService;
import pl.dockerguardimage.data.functionality.notification.domain.Notification;
import pl.dockerguardimage.security.functionality.user.context.UserContextHolder;

import java.util.Locale;
import java.util.function.Predicate;

@AllArgsConstructor
@Service
@Transactional
class NotificationGetAndUpdateServiceImpl implements NotificationGetAndUpdateService {

    private final NotificationCudService notificationCudService;
    private final NotificationQueryService notificationQueryService;
    private final MessageSource apiMessageSource;

    @Override
    public Page<NotificationDTO> getAndUpdate(Pageable pageable) {
        var notifications = notificationQueryService.getAllByUser(UserContextHolder.getAuthenticatedUser().id(), pageable);
        notifications.getContent().stream().filter(Predicate.not(Notification::isSeen)).forEach(notification -> {
            notification.setSeen(true);
            notificationCudService.update(notification);
        });

        return notifications.map(notification -> NotificationDTO.builder()
                .id(notification.getId())
                .type(notification.getType())
                .date(notification.getDate())
                .message(buildMessage(notification))
                .elementId(notification.getElementId())
                .build()
        );
    }

    private String buildMessage(Notification notification) {
        return switch (notification.getType()) {
            case FILE_ACCESS_ADD_READ, FILE_ACCESS_REMOVE, FILE_ACCESS_ADD_WRITE, FILE_ACCESS_UPDATE_READ, FILE_ACCESS_UPDATE_WRITE, COMMENT ->
                    apiMessageSource.getMessage(notification.getMessage(), new String[]{notification.getUsername(), notification.getAdditionalInformation()}, new Locale(UserContextHolder.getAuthenticatedUser().locale()));
            case SCAN_COMPLETED, SCAN_ERROR ->
                    apiMessageSource.getMessage(notification.getMessage(), new String[]{notification.getAdditionalInformation()}, new Locale(UserContextHolder.getAuthenticatedUser().locale()));
        };

    }

}
