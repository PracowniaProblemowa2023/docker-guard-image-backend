package pl.dockerguardimage.data.functionality.fileaccess.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dockerguardimage.data.functionality.accesstype.domain.AccessType;
import pl.dockerguardimage.data.functionality.common.domain.EntityId;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.user.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "fileaccess")
@NoArgsConstructor
public class FileAccess implements EntityId<FileAccessId> {

    @EmbeddedId
    private FileAccessId id;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDateTime date;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "file_access_id", referencedColumnName = "access_type_id")
    private AccessType accessType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="image_scan_id", nullable=false, insertable=false, updatable=false)
    private ImageScan imageScan;

}
