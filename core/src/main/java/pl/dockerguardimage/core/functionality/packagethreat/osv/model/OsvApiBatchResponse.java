package pl.dockerguardimage.core.functionality.packagethreat.osv.model;

import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;

public record OsvApiBatchResponse (SyftPayload syftPayload, OsvApiResponse response){}
