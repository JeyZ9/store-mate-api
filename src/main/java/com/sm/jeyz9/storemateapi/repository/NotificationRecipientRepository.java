package com.sm.jeyz9.storemateapi.repository;

import com.sm.jeyz9.storemateapi.models.NotificationRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Long> {
}
