package pl.dockerguardimage.data.functionality.packagethreat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.common.service.CudServiceImpl;
import pl.dockerguardimage.data.functionality.packagethreat.domain.PackageThreat;

@Service
@Transactional
class PackageThreatCudServiceImpl extends CudServiceImpl<PackageThreatRepository, PackageThreat, Long> implements PackageThreatCudService {
    public PackageThreatCudServiceImpl(PackageThreatRepository repository) {
        super(repository);
    }
}
