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
@Table(name = "dockeruser")
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    @NotEmpty
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty
    private String password;

    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @Column(name = "locale", nullable = false)
    @NotEmpty
    private String locale;

    @OneToMany(mappedBy="user")
    private Set<FileAccess> fileAccesses;

    @ManyToMany(mappedBy = "users")
    private Set<Notification> notifications = new HashSet<>();

}
