package pl.dockerguardimage.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
