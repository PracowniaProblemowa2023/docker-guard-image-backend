package pl.dockerguardimage.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "IMAGE_SCAN")
@NoArgsConstructor
public class ImageScan {

    @Id
    @Column(name = "image_scan_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String imageScanId;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToMany(mappedBy="imageScan")
    private Set<SyftPayload> syftPayloads;

    @OneToMany(mappedBy="imageScan")
    private Set<Comment> comments;

    @OneToMany(mappedBy="imageScan")
    private Set<FileAccess> fileAccesses;


}
