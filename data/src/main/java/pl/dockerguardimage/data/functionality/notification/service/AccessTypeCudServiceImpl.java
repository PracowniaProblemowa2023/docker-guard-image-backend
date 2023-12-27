package pl.dockerguardimage.data.functionality.notification.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.accesstype.domain.AccessType;
import pl.dockerguardimage.data.functionality.common.service.CudServiceImpl;

@Transactional
@Service
class AccessTypeCudServiceImpl extends CudServiceImpl<AccessTypeRepository, AccessType, Long> implements AccessTypeCudService {

    public AccessTypeCudServiceImpl(AccessTypeRepository repository) {
        super(repository);
    }

}
