package pl.dockerguardimage.core.functionality.packagethreat.cve.model;

public class CveApiMapperService {

    public static String getSeverity(CveApiResponse response) {
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
