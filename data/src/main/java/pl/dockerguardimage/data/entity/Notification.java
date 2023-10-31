package pl.dockerguardimage.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
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
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String notificationId;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "message")
    @NotEmpty
    private String message;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "notification_user",
            joinColumns = { @JoinColumn(referencedColumnName = "notification_id") },
            inverseJoinColumns = { @JoinColumn(referencedColumnName = "user_id") })
    Set<User> users = new HashSet<>();


}
