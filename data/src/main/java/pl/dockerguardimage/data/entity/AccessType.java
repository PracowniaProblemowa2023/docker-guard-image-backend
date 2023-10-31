package pl.dockerguardimage.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "accesstype")
@NoArgsConstructor
public class AccessType {

    @Id
    @Column(name = "access_type_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    @NotNull
    private String name;

    @Column(name = "description", nullable = false)
    @NotEmpty
    private String description;

    @OneToOne(mappedBy = "accessType")
    private FileAccess fileAccess;

}
