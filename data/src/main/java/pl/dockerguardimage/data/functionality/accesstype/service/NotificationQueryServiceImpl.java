package pl.dockerguardimage.data.functionality.accesstype.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.notification.domain.Notification;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationRepository repository;

    @Override
    public Page<Notification> getAllByUser(Long id, Pageable pageable) {
        return repository.findAllByUser(id, pageable);
    }
}
