package pl.dockerguardimage.core.functionality.osv.model;

public class OsvApiMapperService {

    public static String getSeverityFromVulnerability(OsvApiResponse.OsvApiVulnerability vulnerability) {
        String severity = "";
        if (vulnerability.affected().get(0).ecosystemSpecific() != null &&
                vulnerability.affected().get(0).ecosystemSpecific().severity() != null) {
            severity = vulnerability.affected().get(0).ecosystemSpecific().severity();
        } else if (vulnerability.databaseSpecific() != null) {
            severity = vulnerability.databaseSpecific().severity();
        }
        return severity;
    }

}
