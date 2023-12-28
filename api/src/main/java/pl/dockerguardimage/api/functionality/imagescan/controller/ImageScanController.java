package pl.dockerguardimage.api.functionality.imagescan.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.dockerguardimage.api.functionality.imagescan.mapper.ImageScanMapper;
import pl.dockerguardimage.api.functionality.imagescan.model.ImageScanIndexResponse;
import pl.dockerguardimage.api.functionality.imagescan.model.ImageScanRequest;
import pl.dockerguardimage.api.functionality.imagescan.model.ImageScanResponse;
import pl.dockerguardimage.api.functionality.imagescan.model.ImageScanStateResponse;
import pl.dockerguardimage.core.functionality.imagescan.dto.ImageScanCreateDTO;
import pl.dockerguardimage.core.functionality.imagescan.dto.ImageScanGetDTO;
import pl.dockerguardimage.core.functionality.imagescan.service.ImageScanCreateService;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanQueryService;
import pl.dockerguardimage.security.functionality.user.context.UserContextHolder;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/imagescan")
public class ImageScanController {

    private final ImageScanCreateService imageScanCreateService;
    private final ImageScanQueryService imageScanQueryService;

    @PostMapping
    public ResponseEntity<ImageScanGetDTO> scan(@Validated @RequestBody ImageScanRequest request) {
        var dto = ImageScanCreateDTO.builder()
                .image(request.getImage())
                .build();
        return ResponseEntity.ok(imageScanCreateService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ImageScanIndexResponse>> getAll() {
        var results = imageScanQueryService.getAllByUserId(UserContextHolder.getAuthenticatedUser().id());
        return ResponseEntity.ok(ImageScanMapper.mapImageScanIndex(results));
    }

    @GetMapping("/result")
    public ResponseEntity<ImageScanResponse> result(@RequestParam Long id) {
        var result = imageScanQueryService.getById(id);
        return ResponseEntity.ok(ImageScanMapper.mapImageScanToImageScanResponse(result));
    }

    @GetMapping("/state")
    public ResponseEntity<ImageScanStateResponse> state(@RequestParam Long id) {
        var result = imageScanQueryService.getById(id);
        return ResponseEntity.ok(ImageScanMapper.mapImageScanState(result));
    }
}
