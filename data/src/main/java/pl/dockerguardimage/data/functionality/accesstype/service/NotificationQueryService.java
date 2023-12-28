package pl.dockerguardimage.data.functionality.accesstype.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.dockerguardimage.data.functionality.notification.domain.Notification;

public interface NotificationQueryService {
    Page<Notification> getAllByUser(Long id, Pageable pageable);
}
