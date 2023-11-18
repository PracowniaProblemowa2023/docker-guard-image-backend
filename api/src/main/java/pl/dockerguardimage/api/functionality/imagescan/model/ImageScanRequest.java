package pl.dockerguardimage.api.functionality.imagescan.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dockerguardimage.core.validator.annotation.NotNullOrEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageScanRequest {

    @NotNullOrEmpty
    private String name;
    @NotNullOrEmpty
    private String image;

}
