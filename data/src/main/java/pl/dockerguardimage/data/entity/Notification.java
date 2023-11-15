package pl.dockerguardimage.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.dockerguardimage.data.functionality.user.domain.User;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "notification")
@NoArgsConstructor
public class Notification {

    @Id
    @Column(name = "notification_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "message", nullable = false)
    @NotEmpty
    private String message;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "notification_user",
            joinColumns = { @JoinColumn(referencedColumnName = "notification_id") },
            inverseJoinColumns = { @JoinColumn(referencedColumnName = "user_id") })
    Set<User> users = new HashSet<>();


}
