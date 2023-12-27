package pl.dockerguardimage.data.functionality.notification.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional(readOnly = true)
class AccessTypeQueryServiceImpl implements AccessTypeQueryService {
}
