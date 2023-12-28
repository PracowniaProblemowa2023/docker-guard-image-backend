package pl.dockerguardimage.api.functionality.notification.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dockerguardimage.core.functionality.notification.dto.NotificationDTO;
import pl.dockerguardimage.core.functionality.notification.service.NotificationGetAndUpdateService;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationGetAndUpdateService notificationGetAndUpdateService;

    @GetMapping
    public ResponseEntity<Page<NotificationDTO>> get(@PageableDefault(size = 10, page = 0, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(notificationGetAndUpdateService.getAndUpdate(pageable));
    }

}
