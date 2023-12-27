package pl.dockerguardimage.data.functionality.imagescan.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dockerguardimage.data.entity.Comment;
import pl.dockerguardimage.data.entity.FileAccess;
import pl.dockerguardimage.data.functionality.common.domain.EntityId;
import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;
import pl.dockerguardimage.data.functionality.user.domain.User;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "imagescan")
@NoArgsConstructor
public class ImageScan implements EntityId<Long> {

    @Id
    @Column(name = "image_scan_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_name", nullable = false)
    @NotEmpty
    private String imageName;

    @Column(name = "date", nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotEmpty
    private Result result = Result.STARTED;

    private String errorMsg;

    @OneToMany(mappedBy = "imageScan")
    private Set<SyftPayload> syftPayloads = new HashSet<>();

    @OneToMany(mappedBy = "imageScan")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "imageScan")
    private Set<FileAccess> fileAccesses = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    public void setErrorMsg(String errorMsg) {
        this.result = Result.ERROR;
        this.errorMsg = errorMsg;
    }

    public void addSyftPayload(SyftPayload syftPayload) {
        getSyftPayloads().add(syftPayload);
        syftPayload.setImageScan(this);
    }
}
