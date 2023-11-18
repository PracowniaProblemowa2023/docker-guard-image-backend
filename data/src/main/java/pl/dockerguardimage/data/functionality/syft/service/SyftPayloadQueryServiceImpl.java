package pl.dockerguardimage.data.functionality.syft.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@AllArgsConstructor
class SyftPayloadQueryServiceImpl implements SyftPayloadQueryService {

    private final SyftPayloadRepository repository;

}
