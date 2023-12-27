package pl.dockerguardimage.api.functionality.fileaccess.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.dockerguardimage.api.functionality.fileaccess.model.FileAccessResponse;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileAccessMapper {

    public static List<FileAccessResponse> map(Collection<FileAccess> fileAccesses) {
        return fileAccesses.stream().map(fa -> FileAccessResponse.builder()
                        .id(fa.getUser().getId())
                        .permission(fa.getAccessType().getName())
                        .fullName(fa.getUser().getFullName())
                        .build())
                .sorted(Comparator.comparing(FileAccessResponse::fullNameToLoweCase))
                .collect(Collectors.toList());
    }

}
