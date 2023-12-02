package pl.dockerguardimage.data.functionality.packagethreatcve.service;


import pl.dockerguardimage.data.functionality.packagethreatcve.domain.PackageThreatCve;

import java.util.Optional;

public interface PackageThreatCveQueryService {

    Optional<PackageThreatCve> getOptByName(String name);

    PackageThreatCve getById(Long PackageThreatCveId);
}
