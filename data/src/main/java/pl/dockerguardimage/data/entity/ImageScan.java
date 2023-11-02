package pl.dockerguardimage.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "imagescan")
@NoArgsConstructor
public class ImageScan {

    @Id
    @Column(name = "image_scan_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    private String name;

    @Column(name = "url", nullable = false)
    @NotEmpty
    private String url;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @OneToMany(mappedBy="imageScan")
    private Set<SyftPayload> syftPayloads;

    @OneToMany(mappedBy="imageScan")
    private Set<Comment> comments;

    @OneToMany(mappedBy="imageScan")
    private Set<FileAccess> fileAccesses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User author;
}
