package pl.dockerguardimage.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

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
    @NotEmpty
    private String name;

    @Column(name = "description")
    @NotEmpty
    private String description;

    @OneToOne(mappedBy = "accessType")
    private FileAccess fileAccess;

}
