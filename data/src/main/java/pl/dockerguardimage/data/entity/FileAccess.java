package pl.dockerguardimage.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "fileaccess")
@NoArgsConstructor
public class FileAccess {

    @Id
    @Column(name = "file_access_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String fileAccessId;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_access_id", referencedColumnName = "access_type_id")
    private AccessType accessType;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="image_scan_id", nullable=false)
    private ImageScan imageScan;

}
