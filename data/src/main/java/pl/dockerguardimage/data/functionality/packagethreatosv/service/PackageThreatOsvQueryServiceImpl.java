package pl.dockerguardimage.data.functionality.packagethreatosv.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.packagethreatosv.domain.PackageThreatOsv;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
class PackageThreatOsvQueryServiceImpl implements PackageThreatOsvQueryService {

    private final PackageThreatOsvRepository repository;

    @Override
    public Optional<PackageThreatOsv> getOptByName(String name) {
        return repository.findOptByName(name);
    }

    @Override
    public PackageThreatOsv getById(Long PackageThreatOsvId) {
        return repository.findById(PackageThreatOsvId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
