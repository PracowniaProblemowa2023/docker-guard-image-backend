package pl.dockerguardimage.data.functionality.accesstype.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional(readOnly = true)
class NotificationQueryServiceImpl implements NotificationQueryService {
}
