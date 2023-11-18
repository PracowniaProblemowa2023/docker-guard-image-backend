package pl.dockerguardimage.core.functionality.syft.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dockerguardimage.core.functionality.common.properties.SyftProperties;
import pl.dockerguardimage.core.functionality.syft.execption.SyftException;
import pl.dockerguardimage.core.functionality.syft.model.SyftPayloadDTO;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanCudService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class SyftPayloadParserServiceImpl implements SyftPayloadParserService {

    private final SyftProperties syftProperties;
    private final ImageScanCudService imageScanCudService;
    private final Pattern pattern = Pattern.compile("(\\S+)\\s+(\\S+)\\s+(\\S+)");

    @Override
    public Set<SyftPayloadDTO> parse(ImageScan imageScan) {
        var path = String.format(syftProperties.getDirectory(), imageScan.getId());
        try {
            var lines = Files.lines(Path.of(path)).collect(Collectors.toList());
            return parse(lines);
        } catch (IOException e) {
            imageScan.setErrorMsg("Cannot parse file " + path + "\n" + e.getMessage());
            imageScanCudService.update(imageScan);
            throw new SyftException("Cannot parse file " + path);
        }
    }

    private Set<SyftPayloadDTO> parse(List<String> lines) {
        var syftPayloads = new HashSet<SyftPayloadDTO>();
        for (var line : lines.subList(1, lines.size())) {
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                var dto = SyftPayloadDTO.builder()
                        .name(matcher.group(1))
                        .version(matcher.group(2))
                        .type(matcher.group(3))
                        .build();
                syftPayloads.add(dto);
            }
        }
        return syftPayloads;
    }

}
