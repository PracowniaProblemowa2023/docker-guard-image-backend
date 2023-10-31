package pl.dockerguardimage.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "SYFT_PAYLOAD")
@NoArgsConstructor
public class SyftPayload {

    @Id
    @Column(name = "syft_paylaod_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    private String name;

    @Column(name = "version", nullable = false)
    @NotEmpty
    private String version;

    @Column(name = "type", nullable = false)
    @NotEmpty
    private String type;

    @Enumerated(EnumType.STRING)
    @NotEmpty
    private Result result;

    @OneToMany(mappedBy="syftPayload")
    private Set<PackageThreat> packageThreats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="image_scan_id", nullable=false)
    private ImageScan imageScan;
}
