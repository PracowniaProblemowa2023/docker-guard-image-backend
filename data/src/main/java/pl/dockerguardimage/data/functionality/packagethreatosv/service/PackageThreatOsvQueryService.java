package pl.dockerguardimage.data.functionality.packagethreatosv.service;

import pl.dockerguardimage.data.functionality.packagethreatosv.domain.PackageThreatOsv;

import java.util.Optional;

public interface PackageThreatOsvQueryService {

    Optional<PackageThreatOsv> getOptByName(String name);

    PackageThreatOsv getById(Long PackageThreatOsvId);
}
