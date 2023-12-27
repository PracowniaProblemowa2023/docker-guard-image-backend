package pl.dockerguardimage.data.functionality.packagethreatosv.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dockerguardimage.data.functionality.common.domain.EntityId;
import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "packagethreatosv")
@NoArgsConstructor
public class PackageThreatOsv implements EntityId<Long> {

    @Id
    @Column(name = "package_threat_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "osv_id")
    @NotEmpty
    private String osvId;

    @Column(name = "summary", columnDefinition="text")
    @NotEmpty
    private String summary;

    @Column(name = "aliases")
    @NotEmpty
    private String aliases;

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
