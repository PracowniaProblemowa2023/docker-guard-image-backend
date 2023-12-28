package pl.dockerguardimage.api.functionality.fileaccess.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.dockerguardimage.api.functionality.fileaccess.mapper.FileAccessMapper;
import pl.dockerguardimage.api.functionality.fileaccess.model.FileAccessRemoveRequest;
import pl.dockerguardimage.api.functionality.fileaccess.model.FileAccessRequest;
import pl.dockerguardimage.api.functionality.fileaccess.model.FileAccessResponse;
import pl.dockerguardimage.core.functionality.fileaccess.dto.FileAccessDTO;
import pl.dockerguardimage.core.functionality.fileaccess.dto.FileAccessRemoveDTO;
import pl.dockerguardimage.core.functionality.fileaccess.service.FileAccessService;
import pl.dockerguardimage.core.validator.annotation.UserHasAccessToImageScan;
import pl.dockerguardimage.data.functionality.fileaccess.service.FileAccessQueryService;

import java.util.List;

@Validated
@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/fileaccess")
public class FileAccessController {

    private final FileAccessService fileAccessService;
    private final FileAccessQueryService fileAccessQueryService;

    @PostMapping
    public ResponseEntity<?> manageAccess(@Validated @RequestBody FileAccessRequest request) {
        var dto = FileAccessDTO.builder()
                .imageScanId(request.imageScanId())
                .userId(request.userId())
                .permission(request.permission())
                .build();
        fileAccessService.process(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{imageScanId}")
    public ResponseEntity<List<FileAccessResponse>> get(@PathVariable("imageScanId") @UserHasAccessToImageScan Long imageScanId) {
        var fileAccesses = fileAccessQueryService.getAllByImageScanId(imageScanId);
        return ResponseEntity.ok(FileAccessMapper.map(fileAccesses));
    }

    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody FileAccessRemoveRequest request) {
        var dto = FileAccessRemoveDTO.builder()
                .imageScanId(request.imageScanId())
                .userId(request.userId())
                .build();
        fileAccessService.delete(dto);
        return ResponseEntity.ok().build();
    }

}
