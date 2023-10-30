package pl.dockerguardimage.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification,String> {
}
