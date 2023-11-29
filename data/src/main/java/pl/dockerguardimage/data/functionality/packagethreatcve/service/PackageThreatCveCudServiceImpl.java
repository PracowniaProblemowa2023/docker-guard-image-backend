package pl.dockerguardimage.data.functionality.packagethreatcve.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.common.service.CudServiceImpl;
import pl.dockerguardimage.data.functionality.packagethreatcve.domain.PackageThreatCve;

@Service
@Transactional
class PackageThreatCveCudServiceImpl extends CudServiceImpl<PackageThreatCveRepository, PackageThreatCve, Long> implements PackageThreatCveCudService {
    public PackageThreatCveCudServiceImpl(PackageThreatCveRepository repository) {
        super(repository);
    }
}
