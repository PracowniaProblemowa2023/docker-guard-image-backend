package pl.dockerguardimage.data.functionality.syft.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dockerguardimage.data.functionality.common.domain.EntityId;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.packagethreat.domain.PackageThreat;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "syftpayload")
@NoArgsConstructor
public class SyftPayload implements EntityId<Long> {

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

    @OneToMany(mappedBy = "syftPayload")
    private Set<PackageThreat> packageThreats = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_scan_id", nullable = false)
    private ImageScan imageScan;

    public void addPackageThreat(PackageThreat packageThreat) {
        getPackageThreats().add(packageThreat);
        packageThreat.setSyftPayload(this);
    }
}
