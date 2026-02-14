package com.sm.jeyz9.storemateapi.services;

import com.sm.jeyz9.storemateapi.dto.ChangePasswordDTO;
import com.sm.jeyz9.storemateapi.dto.LoginDTO;
import com.sm.jeyz9.storemateapi.dto.PasswordResetDTO;
import com.sm.jeyz9.storemateapi.dto.RegisterDTO;

public interface AuthService {
    String register(RegisterDTO require);
    String login(LoginDTO require);
    String resetPassword(PasswordResetDTO request);
    String changePassword(String email, ChangePasswordDTO request);
}
