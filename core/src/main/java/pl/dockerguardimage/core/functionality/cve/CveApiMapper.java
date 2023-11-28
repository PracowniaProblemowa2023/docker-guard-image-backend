package pl.dockerguardimage.core.functionality.cve;

import com.example.dockerguarddevelopment.osv.dto.response.OsvApiResponse;
import com.example.dockerguarddevelopment.osv.dto.response.SystemResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CveApiMapper {

    public static List<String> mapOsvApiResponseToCveList(OsvApiResponse osvApiResponse) {
        return osvApiResponse
                .vulnerabilities()
                .stream()
                .map(response -> response.aliases().get(0))
                .collect(Collectors.toList());
    }

    public static SystemResponse.SystemVulnerability mapCveApiResponseToSystemVulnerability(CveApiResponse response) {

        return new SystemResponse.SystemVulnerability(
                response.id(),
                response.summary(),
                response.summary(),
                response.modified(),
                response.published(),
                mapCveApiResponseToSeverity(response),
                response.references()
        );

    }

    private static String mapCveApiResponseToSeverity(CveApiResponse response) {
        return response.msbulletin() != null
                ? response.msbulletin().get(0).severity()
                : mapCvssScoreToSeverity(response.cvss());
    }

    private static String mapCvssScoreToSeverity(double score) {
        if (score >= 0.0 && score <= 3.9) {
            return "LOW";
        } else if (score >= 4.0 && score <= 6.9) {
            return "MEDIUM";
        } else if (score >= 7.0 && score <= 8.9) {
            return "HIGH";
        } else if (score >= 9.0 && score <= 10.0) {
            return "SEVERE";
        } else {
            return "UNKNOWN";
        }
    }
}
