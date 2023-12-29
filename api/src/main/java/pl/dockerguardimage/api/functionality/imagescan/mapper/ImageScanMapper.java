package pl.dockerguardimage.api.functionality.imagescan.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.dockerguardimage.api.functionality.imagescan.model.*;
import pl.dockerguardimage.data.functionality.accesstype.domain.AccessType;
import pl.dockerguardimage.data.functionality.accesstype.domain.AccessTypePermission;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.packagethreatcve.domain.PackageThreatCve;
import pl.dockerguardimage.data.functionality.packagethreatosv.domain.PackageThreatOsv;
import pl.dockerguardimage.security.functionality.user.context.UserContextHolder;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
                osv.getSeverity(),
                osv.getAliases()
        );
    }

    public static PackageThreatDto mapCveToPackageDto(PackageThreatCve cve) {
        return new PackageThreatDto(
                cve.getCveId(),
                cve.getSummary(),
                cve.getDetails(),
                cve.getModified(),
                cve.getPublished(),
                cve.getSeverity(),
                cve.getCveId()
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
                .permission(UserContextHolder.getAuthenticatedUser().id().equals(imageScan.getAuthor().getId()) ? AccessTypePermission.WRITE : AccessTypePermission.READ)
                .result(imageScan.getResult())
                .author(imageScan.getAuthor().getFullName())
                .payloads(syftPayloadDtos)
                .build();

    }

    public static List<ImageScanIndexResponse> mapImageScanIndex(Set<ImageScan> results) {
        var currentUserId = UserContextHolder.getAuthenticatedUser().id();
        return results.stream().map(imageScan -> ImageScanIndexResponse.builder()
                        .id(imageScan.getId())
                        .imageName(imageScan.getImageName())
                        .date(imageScan.getDate())
                        .result(imageScan.getResult())
                        .author(imageScan.getAuthor().getFullName())
                        .permission(Objects.equals(imageScan.getAuthor().getId(), currentUserId) ? "OWNER" :
                                imageScan.getFileAccesses().stream().filter(fa -> Objects.equals(fa.getUser().getId(), currentUserId))
                                        .map(FileAccess::getAccessType)
                                        .map(AccessType::getName)
                                        .map(AccessTypePermission::name)
                                        .findFirst().get())
                        .build())
                .sorted(Comparator.comparing(ImageScanIndexResponse::date).reversed())
                .collect(Collectors.toList());
    }
}
