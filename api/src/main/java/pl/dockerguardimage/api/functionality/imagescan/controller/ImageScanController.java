package pl.dockerguardimage.api.functionality.imagescan.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.dockerguardimage.api.functionality.imagescan.model.ImageScanRequest;
import pl.dockerguardimage.core.functionality.imagescan.dto.ImageScanCreateDTO;
import pl.dockerguardimage.core.functionality.imagescan.dto.ImageScanGetDTO;
import pl.dockerguardimage.core.functionality.imagescan.service.ImageScanCreateService;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/imagescan")
public class ImageScanController {

    private final ImageScanCreateService imageScanCreateService;

    @PostMapping
    public ResponseEntity<ImageScanGetDTO> scan(@Validated @RequestBody ImageScanRequest request) {
        var dto = ImageScanCreateDTO.builder()
                .name(request.getName())
                .image(request.getImage())
                .build();
        return ResponseEntity.ok(imageScanCreateService.create(dto));
    }

}
