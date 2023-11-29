package pl.dockerguardimage.data.functionality.packagethreat.service;

import pl.dockerguardimage.data.functionality.packagethreat.domain.PackageThreat;

import java.util.Optional;

public interface PackageThreatQueryService {

    Optional<PackageThreat> getOptByName(String name);

    PackageThreat getById(Long PackageThreatId);
}
