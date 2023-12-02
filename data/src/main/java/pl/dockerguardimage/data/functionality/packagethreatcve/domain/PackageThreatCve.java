package pl.dockerguardimage.data.functionality.packagethreatcve.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dockerguardimage.data.functionality.common.domain.EntityId;
import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "packagethreatcve")
@NoArgsConstructor
public class PackageThreatCve implements EntityId<Long> {

    @Id
    @Column(name = "package_threat_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cve_id")
    @NotEmpty
    private String cveId;

    @Column(name = "summary", columnDefinition="text")
    @NotEmpty
    private String summary;

    @Column(name = "details", columnDefinition="text")
    @NotEmpty
    private String details;

    @Column(name = "modified")
    private ZonedDateTime modified;

    @Column(name = "published")
    private ZonedDateTime published;

    @Column(name = "severity")
    @NotEmpty
    private String severity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="syft_payload_id", nullable=false)
    private SyftPayload syftPayload;

}
