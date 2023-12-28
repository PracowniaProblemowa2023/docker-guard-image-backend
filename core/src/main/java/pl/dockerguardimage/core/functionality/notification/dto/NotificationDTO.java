package pl.dockerguardimage.core.functionality.notification.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import pl.dockerguardimage.data.functionality.notification.domain.NotificationType;

import java.time.LocalDateTime;

@Builder
public record NotificationDTO(Long id,
                              @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date,
                              NotificationType type,
                              String message,
                              Long elementId
) {
}
