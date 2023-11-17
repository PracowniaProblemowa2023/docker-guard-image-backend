package pl.dockerguardimage.core.osv.dto.mapper;

import pl.dockerguardimage.core.osv.dto.request.OsvApiRequest;
import pl.dockerguardimage.core.osv.dto.request.SystemRequestService;
import pl.dockerguardimage.core.osv.dto.response.OsvApiResponse;
import pl.dockerguardimage.core.osv.dto.response.SystemResponseService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OsvApiMapperService {

    public static OsvApiRequest mapSystemToOsvApiRequest(SystemRequestService request) {
        return new OsvApiRequest(new OsvApiRequest.OsvPackage(request.packageName(), request.ecosystem()), request.version());
    }

    public static SystemResponseService mapOsvApiToSystemResponse(OsvApiResponse response, SystemRequestService request) {
        return new SystemResponseService(
                request.packageName(),
                request.version(),
                request.ecosystem(),
                mapOsvApiToVulnerabilityList(response)
        );
    }

    public static List<SystemResponseService.SystemVulnerability> mapOsvApiToVulnerabilityList(OsvApiResponse response) {
        return response.vulnerabilities() == null
                ? Collections.emptyList()
                : response.vulnerabilities()
                .stream()
                .map(OsvApiMapperService::mapOsvApiToSystemVulnerability)
                .collect(Collectors.toList());
    }

    private static String getSeverityFromVulnerability(OsvApiResponse.OsvApiVulnerability vulnerability) {
        String severity = "";
        if (vulnerability.affected().get(0).ecosystemSpecific() != null &&
                vulnerability.affected().get(0).ecosystemSpecific().severity() != null) {
            severity = vulnerability.affected().get(0).ecosystemSpecific().severity();
        } else if (vulnerability.databaseSpecific() != null) {
            severity = vulnerability.databaseSpecific().severity();
        }
        return severity;
    }

    private static SystemResponseService.SystemVulnerability mapOsvApiToSystemVulnerability(OsvApiResponse.OsvApiVulnerability vulnerability) {
        return new SystemResponseService.SystemVulnerability(vulnerability.id(), vulnerability.summary(), vulnerability.details(), vulnerability.modified(), vulnerability.published(), getSeverityFromVulnerability(vulnerability), vulnerability.aliases());
    }

}
