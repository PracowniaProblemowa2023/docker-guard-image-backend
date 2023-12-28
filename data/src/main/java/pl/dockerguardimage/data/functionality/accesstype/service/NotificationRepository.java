package pl.dockerguardimage.data.functionality.accesstype.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.notification.domain.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n inner join n.users u where u.id = :id")
    Page<Notification> findAllByUser(@Param("id") Long id, Pageable pageable);
}
