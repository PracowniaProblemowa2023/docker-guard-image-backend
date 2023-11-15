package pl.dockerguardimage.data.functionality.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dockerguardimage.data.entity.Comment;
import pl.dockerguardimage.data.entity.FileAccess;
import pl.dockerguardimage.data.entity.ImageScan;
import pl.dockerguardimage.data.entity.Notification;
import pl.dockerguardimage.data.functionality.user.common.domain.EntityId;

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

    @Column(name = "password", nullable = false)
    @NotEmpty
    private String password;

    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @Column(name = "locale", nullable = false)
    @NotEmpty
    private String locale;

    @OneToMany(mappedBy = "user")
    private Set<FileAccess> fileAccesses;

    @OneToMany(mappedBy = "author")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "author")
    private Set<ImageScan> imageScans;

    @ManyToMany(mappedBy = "users")
    private Set<Notification> notifications = new HashSet<>();

}
