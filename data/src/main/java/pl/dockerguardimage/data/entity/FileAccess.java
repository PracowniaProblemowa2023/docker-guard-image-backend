package pl.dockerguardimage.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "FILE_ACCESS")
@NoArgsConstructor
public class FileAccess {

    @Id
    @Column(name = "file_access_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
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
