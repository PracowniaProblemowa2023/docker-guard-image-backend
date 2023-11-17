package pl.dockerguardimage.core.osv.dto.request;

public record SystemRequestService(String packageName, String version, String ecosystem) {
}
