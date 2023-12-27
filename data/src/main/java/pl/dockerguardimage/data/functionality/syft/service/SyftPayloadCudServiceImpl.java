package pl.dockerguardimage.data.functionality.syft.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.common.service.CudServiceImpl;
import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;

@Transactional
@Service
class SyftPayloadCudServiceImpl extends CudServiceImpl<SyftPayloadRepository, SyftPayload, Long> implements SyftPayloadCudService {

    public SyftPayloadCudServiceImpl(SyftPayloadRepository repository) {
        super(repository);
    }
}
