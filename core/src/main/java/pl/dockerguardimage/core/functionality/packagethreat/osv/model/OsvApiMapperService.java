package pl.dockerguardimage.core.functionality.packagethreat.osv.model;

public class OsvApiMapperService {

    public static String getSeverityFromVulnerability(OsvApiResponse.OsvApiVulnerability vulnerability) {
        if (vulnerability.affected().get(0).ecosystemSpecific() != null &&
                vulnerability.affected().get(0).ecosystemSpecific().severity() != null) {
            return vulnerability.affected().get(0).ecosystemSpecific().severity();
        }
        if (vulnerability.databaseSpecific() != null) {
            return vulnerability.databaseSpecific().severity();
        }
        return "";
    }

}
