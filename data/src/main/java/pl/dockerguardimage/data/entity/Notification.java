package pl.dockerguardimage.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "NOTIFICATION")
@NoArgsConstructor
public class Notification {

    @Id
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String notificationId;

    @Column(name = "type")
    private NotificationType type;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "notification_user",
            joinColumns = { @JoinColumn(referencedColumnName = "notification_id") },
            inverseJoinColumns = { @JoinColumn(referencedColumnName = "user_id") })
    Set<User> users = new HashSet<>();


}
