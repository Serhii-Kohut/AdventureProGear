package com.example.adventureprogearjava.services;

import com.example.adventureprogearjava.dto.resetPasswordDto.PasswordResetDTO;
import com.example.adventureprogearjava.dto.resetPasswordDto.PasswordResetRequestDTO;

public interface PasswordResetService {
    void requestPasswordReset(PasswordResetRequestDTO passwordResetRequestDTO, String appUrl);
    void resetPassword(PasswordResetDTO passwordResetDTO);
}
