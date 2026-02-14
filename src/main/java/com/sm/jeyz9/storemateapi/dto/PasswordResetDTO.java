package com.sm.jeyz9.storemateapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDTO {
    @NotBlank(message = "Token is required")
    private String token;
    @Pattern(
            regexp = "^(?=.*[A-Za-z]).{8,}$", 
            message = "Must be at least 8 characters long and contain at least one letter (A–Z)."
    )
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
}
