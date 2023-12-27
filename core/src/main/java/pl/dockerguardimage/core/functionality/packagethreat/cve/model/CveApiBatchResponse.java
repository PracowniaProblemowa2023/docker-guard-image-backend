package pl.dockerguardimage.core.functionality.packagethreat.cve.model;

import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;

public record CveApiBatchResponse(SyftPayload syftPayload, CveApiResponse response){
}
