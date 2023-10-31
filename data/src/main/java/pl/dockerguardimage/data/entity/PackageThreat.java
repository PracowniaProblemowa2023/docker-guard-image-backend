package pl.dockerguardimage.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "packagethreat")
@NoArgsConstructor
public class PackageThreat {

    @Id
    @Column(name = "package_threat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "osv_id")
    @NotEmpty
    private String osvId;

    @Column(name = "summary")
    @NotEmpty
    private String summary;

    @Column(name = "details")
    @NotEmpty
    private String details;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "published")
    private LocalDateTime published;

    @Column(name = "severity")
    @NotEmpty
    private String severity;

    @Column(name = "versions")
    @NotEmpty
    private String versions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="syft_payload_id", nullable=false)
    private SyftPayload syftPayload;

}
