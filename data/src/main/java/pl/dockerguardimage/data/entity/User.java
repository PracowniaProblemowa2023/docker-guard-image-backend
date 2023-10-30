package pl.dockerguardimage.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "DOCKER_USER'")
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;

    @Column(name = "username")
    @NotEmpty
    private String username;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "locale")
    @NotEmpty
    private String locale;

    @OneToMany(mappedBy="user")
    private Set<FileAccess> fileAccesses;

    @ManyToMany(mappedBy = "users")
    private Set<Notification> notifications = new HashSet<>();

}
