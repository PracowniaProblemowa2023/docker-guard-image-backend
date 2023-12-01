package pl.dockerguardimage.api.functionality.imagescan.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.dockerguardimage.api.functionality.imagescan.model.ImageScanRequest;
import pl.dockerguardimage.api.functionality.imagescan.model.ImageScanResponse;
import pl.dockerguardimage.api.functionality.imagescan.model.PackageThreatDto;
import pl.dockerguardimage.api.functionality.imagescan.model.SyftPayloadDto;
import pl.dockerguardimage.core.functionality.imagescan.dto.ImageScanCreateDTO;
import pl.dockerguardimage.core.functionality.imagescan.dto.ImageScanGetDTO;
import pl.dockerguardimage.core.functionality.imagescan.service.ImageScanCreateService;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanQueryService;
import pl.dockerguardimage.data.functionality.packagethreatcve.domain.PackageThreatCve;
import pl.dockerguardimage.data.functionality.packagethreatosv.domain.PackageThreatOsv;

import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/imagescan")
public class ImageScanController {

    private final ImageScanCreateService imageScanCreateService;
    private final ImageScanQueryService imageScanQueryService;

    @PostMapping
    public ResponseEntity<ImageScanGetDTO> scan(@Validated @RequestBody ImageScanRequest request) {
        var dto = ImageScanCreateDTO.builder()
                .name(request.getName())
                .image(request.getImage())
                .build();
        return ResponseEntity.ok(imageScanCreateService.create(dto));
    }

    @GetMapping("/result")
    public ResponseEntity<ImageScanResponse> result(@RequestParam Long id) {
        var result = imageScanQueryService.getById(id);
        var mapped = mapImageScanToImageScanResponse(result);
        return ResponseEntity.ok(mapped);
    }

    @GetMapping("/state")
    public ResponseEntity<String> state(@RequestParam Long id) {
        var result = imageScanQueryService.getById(id);
        return ResponseEntity.ok(result.getResult().name());
    }

    private PackageThreatDto mapOsvToPackageDto(PackageThreatOsv osv) {
        return new PackageThreatDto(
                osv.getOsvId(),
                osv.getSummary(),
                osv.getDetails(),
                osv.getModified(),
                osv.getPublished(),
                osv.getSeverity()
        );
    }

    private PackageThreatDto mapCveToPackageDto(PackageThreatCve osv) {
        return new PackageThreatDto(
                osv.getCveId(),
                osv.getSummary(),
                osv.getDetails(),
                osv.getModified(),
                osv.getPublished(),
                osv.getSeverity()
        );
    }

    private ImageScanResponse mapImageScanToImageScanResponse(ImageScan imageScan) {

        Set<SyftPayloadDto> syftPayloadDtos = imageScan
                .getSyftPayloads()
                .stream()
                .map(syftPayload -> new SyftPayloadDto(
                        syftPayload.getName(),
                        syftPayload.getVersion(),
                        syftPayload.getType(),
                        syftPayload.getPackageThreatsOsv().stream().map(this::mapOsvToPackageDto).collect(Collectors.toSet()),
                        syftPayload.getPackageThreatsCve().stream().map(this::mapCveToPackageDto).collect(Collectors.toSet())
                )).collect(Collectors.toSet());

        return ImageScanResponse.builder()
                .id(imageScan.getId())
                .imageName(imageScan.getImageName())
                .date(imageScan.getDate())
                .result(imageScan.getResult())
                .payloads(syftPayloadDtos)
                .build();

    }

}
