package com.sm.jeyz9.storemateapi.services.impl;

import com.sm.jeyz9.storemateapi.Repository.RoleRepository;
import com.sm.jeyz9.storemateapi.Repository.UserRepository;
import com.sm.jeyz9.storemateapi.config.JwtService;
import com.sm.jeyz9.storemateapi.dto.LoginDTO;
import com.sm.jeyz9.storemateapi.dto.RegisterDTO;
import com.sm.jeyz9.storemateapi.exceptions.WebException;
import com.sm.jeyz9.storemateapi.models.Role;
import com.sm.jeyz9.storemateapi.models.RoleName;
import com.sm.jeyz9.storemateapi.models.User;
import com.sm.jeyz9.storemateapi.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }

    @Override
    public String register(RegisterDTO require) {
        try {
            if (userRepository.existsUserByEmail(require.getEmail())) {
                throw new WebException(HttpStatus.BAD_REQUEST, "Exist by email");
            }

            if (userRepository.existsUserByPhone(require.getPhone())) {
                throw new WebException(HttpStatus.BAD_REQUEST, "Exist by phone");
            }

            if (!require.getPassword().equals(require.getConfirmPassword())) {
                throw new WebException(HttpStatus.BAD_REQUEST, "Password does not match");
            }
            
           
            Role userRole = roleRepository.findByRoleName(RoleName.USER).orElseThrow(() -> new WebException(HttpStatus.NOT_FOUND, "Role not found."));
            Set<Role> roles = Set.of(userRole);
            
            User user = User.builder()
                    .id(null)
                    .name(require.getName())
                    .email(require.getEmail())
                    .phone(require.getPhone())
                    .password(passwordEncoder.encode(require.getPassword()))
                    .roles(roles)
                    .build();
            userRepository.save(user);
            return "Register successfully";
        } catch (WebException e) {
            throw e;
        }catch (Exception e){
            throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error!");
        }
    }

    @Override
    public String login(LoginDTO require) {
        User user = userRepository.findUserByEmail(require.getEmail()).orElseThrow(() -> new WebException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
        
        if(!passwordEncoder.matches(require.getPassword(), user.getPassword())) {
            throw new WebException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRoleName())
                        ).toList()
        );
        
        return jwtService.generateToken(userDetails);
    }
}
