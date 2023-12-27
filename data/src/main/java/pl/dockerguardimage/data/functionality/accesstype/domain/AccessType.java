package pl.dockerguardimage.data.functionality.accesstype.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dockerguardimage.data.functionality.common.domain.EntityId;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "accesstype")
@NoArgsConstructor
public class AccessType implements EntityId<Long> {

    @Id
    @Column(name = "access_type_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    @NotEmpty
    @NotNull
    private AccessTypePermission name;

    @Column(name = "description", nullable = false)
    @NotEmpty
    private String description;

    @OneToOne(mappedBy = "accessType")
    private FileAccess fileAccess;

}
