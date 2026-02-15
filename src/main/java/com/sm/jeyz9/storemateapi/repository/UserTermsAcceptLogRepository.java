package com.sm.jeyz9.storemateapi.repository;

import com.sm.jeyz9.storemateapi.models.UserTermsAcceptLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTermsAcceptLogRepository extends JpaRepository<UserTermsAcceptLog, Long> {
}
