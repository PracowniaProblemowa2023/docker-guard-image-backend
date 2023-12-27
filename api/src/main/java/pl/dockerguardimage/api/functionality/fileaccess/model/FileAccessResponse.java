package pl.dockerguardimage.api.functionality.fileaccess.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import pl.dockerguardimage.data.functionality.accesstype.domain.AccessTypePermission;

@Builder
public record FileAccessResponse(Long id, String fullName, AccessTypePermission permission) {

    @JsonIgnore
    public String fullNameToLoweCase() {
        return fullName.toLowerCase();
    }

}
