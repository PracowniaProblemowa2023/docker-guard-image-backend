package pl.dockerguardimage.data.functionality.accesstype.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.common.service.CudServiceImpl;
import pl.dockerguardimage.data.functionality.notification.domain.Notification;

@Transactional
@Service
class NotificationCudServiceImpl extends CudServiceImpl<NotificationRepository, Notification, Long> implements NotificationCudService {

    public NotificationCudServiceImpl(NotificationRepository repository) {
        super(repository);
    }

}
