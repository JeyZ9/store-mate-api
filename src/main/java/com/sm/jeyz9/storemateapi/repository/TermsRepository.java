package com.sm.jeyz9.storemateapi.repository;

import com.sm.jeyz9.storemateapi.models.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsRepository extends JpaRepository<Terms, Long> {
}
