package pl.dockerguardimage.api.functionality.imagescan.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.dockerguardimage.api.functionality.imagescan.model.ImageScanResponse;
import pl.dockerguardimage.api.functionality.imagescan.model.ImageScanStateResponse;
import pl.dockerguardimage.api.functionality.imagescan.model.PackageThreatDto;
import pl.dockerguardimage.api.functionality.imagescan.model.SyftPayloadDto;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.packagethreatcve.domain.PackageThreatCve;
import pl.dockerguardimage.data.functionality.packagethreatosv.domain.PackageThreatOsv;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageScanMapper {


    public static PackageThreatDto mapOsvToPackageDto(PackageThreatOsv osv) {
        return new PackageThreatDto(
                osv.getOsvId(),
                osv.getSummary(),
                osv.getDetails(),
                osv.getModified(),
                osv.getPublished(),
                osv.getSeverity()
        );
    }

    public static PackageThreatDto mapCveToPackageDto(PackageThreatCve osv) {
        return new PackageThreatDto(
                osv.getCveId(),
                osv.getSummary(),
                osv.getDetails(),
                osv.getModified(),
                osv.getPublished(),
                osv.getSeverity()
        );
    }

    public static ImageScanStateResponse mapImageScanState(ImageScan imageScan) {
        return ImageScanStateResponse.builder()
                .state(imageScan.getResult().name())
                .id(imageScan.getId())
                .name(imageScan.getImageName())
                .build();
    }

    public static ImageScanResponse mapImageScanToImageScanResponse(ImageScan imageScan) {

        var syftPayloadDtos = imageScan
                .getSyftPayloads()
                .stream()
                .map(syftPayload -> new SyftPayloadDto(
                        syftPayload.getName(),
                        syftPayload.getVersion(),
                        syftPayload.getType(),
                        syftPayload.getPackageThreatsOsv().stream().map(ImageScanMapper::mapOsvToPackageDto).collect(Collectors.toSet()),
                        syftPayload.getPackageThreatsCve().stream().map(ImageScanMapper::mapCveToPackageDto).collect(Collectors.toSet())
                )).collect(Collectors.toSet());

        return ImageScanResponse.builder()
                .id(imageScan.getId())
                .imageName(imageScan.getImageName())
                .date(imageScan.getDate())
                .result(imageScan.getResult())
                .author(imageScan.getAuthor().getFullName())
                .payloads(syftPayloadDtos)
                .build();

    }

}
