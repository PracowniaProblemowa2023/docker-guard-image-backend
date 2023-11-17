package pl.dockerguardimage.core.osv.dto.response;

import java.util.List;

public record SystemResponseService(
        String packageName,
        String version,
        String ecosystem,
        List<SystemVulnerability> vulnerabilities) {

    public record SystemVulnerability(String id, String summary, String details, String modified, String published,
                                      String severity, List<String> aliases) {
    }

}
