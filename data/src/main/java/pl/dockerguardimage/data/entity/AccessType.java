package pl.dockerguardimage.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.File;


@Getter
@Setter
@Entity
@Table(name = "ACCESS_TYPE")
@NoArgsConstructor
public class AccessType {

    @Id
    @Column(name = "access_type_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String accessTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "accessType")
    private FileAccess fileAccess;

}
