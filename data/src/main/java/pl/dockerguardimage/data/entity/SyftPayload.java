package pl.dockerguardimage.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "SYFT_PAYLOAD")
@NoArgsConstructor
public class SyftPayload {

    @Id
    @Column(name = "syft_paylaod_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String syftPayloadId;

    @Column(name = "name")
    private String name;

    @Column(name = "version")
    private String version;

    @Column(name = "type")
    private String type;

    @Column(name = "result")
    private Result result;

    @OneToMany(mappedBy="syftPayload")
    private Set<PackageThreat> packageThreats;

    @ManyToOne
    @JoinColumn(name="image_scan_id", nullable=false)
    private ImageScan imageScan;








}
