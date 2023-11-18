package pl.dockerguardimage.data.functionality.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dockerguardimage.data.entity.Comment;
import pl.dockerguardimage.data.entity.FileAccess;
import pl.dockerguardimage.data.entity.Notification;
import pl.dockerguardimage.data.functionality.common.domain.EntityId;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.role.domain.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "dockeruser")
@NoArgsConstructor
public class User implements EntityId<Long> {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    @NotEmpty
    private String username;

    @Column(name = "firstname", nullable = false)
    @NotEmpty
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @NotEmpty
    private String lastname;

    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @Column(name = "locale", nullable = false)
    @NotEmpty
    private String locale = "pl_PL";

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<FileAccess> fileAccesses;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<ImageScan> imageScans;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Notification> notifications = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "dockeruser_role",
            joinColumns = @JoinColumn(name = "dockeruser_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    public void addRole(Role role) {
        getRoles().add(role);
        role.getUsers().add(this);
    }
}
