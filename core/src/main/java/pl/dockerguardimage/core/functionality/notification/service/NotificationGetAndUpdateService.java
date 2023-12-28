package pl.dockerguardimage.core.functionality.notification.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.dockerguardimage.core.functionality.notification.dto.NotificationDTO;

public interface NotificationGetAndUpdateService {
    Page<NotificationDTO> getAndUpdate(Pageable pageable);
}
