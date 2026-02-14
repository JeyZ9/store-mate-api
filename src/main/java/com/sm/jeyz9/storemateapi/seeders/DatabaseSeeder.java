package com.sm.jeyz9.storemateapi.seeders;

import com.sm.jeyz9.storemateapi.repository.RoleRepository;
import com.sm.jeyz9.storemateapi.models.Role;
import com.sm.jeyz9.storemateapi.models.RoleName;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public DatabaseSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if(roleRepository.count() == 0L) {
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(null, RoleName.ADMIN));
            roles.add(new Role(null, RoleName.MODERATOR));
            roles.add(new Role(null, RoleName.USER));
            roleRepository.saveAll(roles);
        }
    }
}
