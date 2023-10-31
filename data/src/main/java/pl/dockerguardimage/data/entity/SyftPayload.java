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
    @Column(name = "syft_payload_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String syftPayloadId;

    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "version")
    @NotEmpty
    private String version;

    @Column(name = "type")
    @NotEmpty
    private String type;

    @Enumerated(EnumType.STRING)
    @NotEmpty
    private Result result;

    @OneToMany(mappedBy="syftPayload")
    private Set<PackageThreat> packageThreats;

    @ManyToOne
    @JoinColumn(name="image_scan_id", nullable=false)
    private ImageScan imageScan;








}
