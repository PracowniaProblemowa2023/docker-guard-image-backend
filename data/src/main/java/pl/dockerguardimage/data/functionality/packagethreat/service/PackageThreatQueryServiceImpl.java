package pl.dockerguardimage.data.functionality.packagethreat.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.packagethreat.domain.PackageThreat;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
class PackageThreatQueryServiceImpl implements PackageThreatQueryService {

    private final PackageThreatRepository repository;

    @Override
    public Optional<PackageThreat> getOptByName(String name) {
        return repository.findOptByName(name);
    }

    @Override
    public PackageThreat getById(Long PackageThreatId) {
        return repository.findById(PackageThreatId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
