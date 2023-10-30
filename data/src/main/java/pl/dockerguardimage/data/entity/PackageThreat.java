package pl.dockerguardimage.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "PACKAGE_THREAT")
@NoArgsConstructor
public class PackageThreat {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "osv_id")
    private String osvId;

    @Column(name = "summary")
    private String summary;

    @Column(name = "details")
    private String details;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "published")
    private LocalDateTime published;

    @Column(name = "severity")
    private String severity;

    @Column(name = "versions")
    private String versions;

    @ManyToOne
    @JoinColumn(name="syft_payload_id", nullable=false)
    private SyftPayload syftPayload;

}
