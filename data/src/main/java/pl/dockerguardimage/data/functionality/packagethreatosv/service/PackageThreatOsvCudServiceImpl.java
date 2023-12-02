package pl.dockerguardimage.data.functionality.packagethreatosv.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.common.service.CudServiceImpl;
import pl.dockerguardimage.data.functionality.packagethreatosv.domain.PackageThreatOsv;

@Service
@Transactional
class PackageThreatOsvCudServiceImpl extends CudServiceImpl<PackageThreatOsvRepository, PackageThreatOsv, Long> implements PackageThreatOsvCudService {
    public PackageThreatOsvCudServiceImpl(PackageThreatOsvRepository repository) {
        super(repository);
    }
}
