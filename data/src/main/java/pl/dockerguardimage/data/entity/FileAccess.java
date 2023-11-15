package pl.dockerguardimage.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dockerguardimage.data.functionality.user.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "fileaccess")
@NoArgsConstructor
public class FileAccess {

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
