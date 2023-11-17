package pl.dockerguardimage.api.dto.mapper;

import pl.dockerguardimage.api.dto.request.SystemRequest;
import pl.dockerguardimage.api.dto.response.SystemResponse;
import pl.dockerguardimage.core.osv.dto.request.SystemRequestService;
import pl.dockerguardimage.core.osv.dto.response.SystemResponseService;

import java.util.stream.Collectors;

public class OsvApiMapperController {

    public static SystemRequestService mapSystemRequestToServiceRequest(SystemRequest request) {
        return new SystemRequestService(
                request.packageName(),
                request.version(),
                request.ecosystem()
        );
    }

    public static SystemResponse mapServiceResponseToSystemResponse(SystemResponseService response) {
        return new SystemResponse(
                response.packageName(),
                response.version(),
                response.ecosystem(),
                response.vulnerabilities()
                        .stream().map(OsvApiMapperController::mapServiceToSystemVulnerability).collect(Collectors.toList())
        );
    }

    private static SystemResponse.SystemVulnerability mapServiceToSystemVulnerability
            (SystemResponseService.SystemVulnerability vulnerability) {
        return new SystemResponse.SystemVulnerability(
                vulnerability.id(),
                vulnerability.summary(),
                vulnerability.details(),
                vulnerability.modified(),
                vulnerability.published(),
                vulnerability.severity(),
                vulnerability.aliases()
        );
    }
}
