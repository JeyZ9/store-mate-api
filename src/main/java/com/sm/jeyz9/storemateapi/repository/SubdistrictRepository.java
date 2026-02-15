package com.sm.jeyz9.storemateapi.repository;

import com.sm.jeyz9.storemateapi.models.Subdistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubdistrictRepository extends JpaRepository<Subdistrict, Long> {
}
