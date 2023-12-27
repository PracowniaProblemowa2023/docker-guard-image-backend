package pl.dockerguardimage.api.functionality.fileaccess.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dockerguardimage.api.functionality.fileaccess.mapper.FileAccessMapper;
import pl.dockerguardimage.api.functionality.fileaccess.model.FileAccessRequest;
import pl.dockerguardimage.core.functionality.fileaccess.dto.FileAccessDTO;
import pl.dockerguardimage.core.functionality.fileaccess.service.FileAccessService;
import pl.dockerguardimage.data.functionality.fileaccess.service.FileAccessQueryService;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/fileaccess")
public class FileAccessController {

    private final FileAccessService fileAccessService;
    private final FileAccessQueryService fileAccessQueryService;

    @PostMapping
    public ResponseEntity<?> manageAccess(FileAccessRequest request) {
        var dto = FileAccessDTO.builder()
                .imageScanId(request.imageScanId())
                .userId(request.userId())
                .permission(request.permission())
                .build();
        fileAccessService.process(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{imageScanId}")
    public ResponseEntity<?> get(@PathVariable("imageScanId") Long imageScanId) {
        var fileAccesses = fileAccessQueryService.getAllByImageScanId(imageScanId);
        return ResponseEntity.ok(FileAccessMapper.map(fileAccesses));
    }

}
