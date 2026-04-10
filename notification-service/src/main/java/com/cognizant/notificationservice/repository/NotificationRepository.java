package com.cognizant.notificationservice.repository;

import com.cognizant.notificationservice.entity.Notification;
import com.cognizant.notificationservice.projection.NotificationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT new com.cognizant.notificationservice.projection.NotificationProjection(n.notificationId, n.userId, n.entityId, n.message, n.category, n.status, n.createdDate) FROM Notification n WHERE n.userId = :userId and n.status='UNREAD' ORDER BY n.createdDate DESC")
    List<NotificationProjection> findAllByUserId(@Param("userId") Long userId);
}
