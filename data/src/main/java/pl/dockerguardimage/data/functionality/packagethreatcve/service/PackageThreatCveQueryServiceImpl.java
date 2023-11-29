package pl.dockerguardimage.data.functionality.packagethreatcve.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.packagethreatcve.domain.PackageThreatCve;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
class PackageThreatCveQueryServiceImpl implements PackageThreatCveQueryService {

    private final PackageThreatCveRepository repository;

    @Override
    public Optional<PackageThreatCve> getOptByName(String name) {
        return repository.findOptByName(name);
    }

    @Override
    public PackageThreatCve getById(Long PackageThreatCveId) {
        return repository.findById(PackageThreatCveId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
