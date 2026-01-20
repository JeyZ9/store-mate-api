package com.sm.jeyz9.storemateapi.Repository;

import com.sm.jeyz9.storemateapi.models.Role;
import com.sm.jeyz9.storemateapi.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}
