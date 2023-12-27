package pl.dockerguardimage.core.functionality.packagethreat.cve.model;

import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;

public record CveApiBatchRequest(SyftPayload syftPayload, CveApiRequest request){}
