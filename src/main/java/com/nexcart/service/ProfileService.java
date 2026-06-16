package com.nexcart.service;

import com.nexcart.dto.profile.ChangePasswordRequest;
import com.nexcart.dto.profile.CustomerProfileResponse;
import com.nexcart.dto.profile.UpdateProfileRequest;

public interface ProfileService {

    CustomerProfileResponse getProfile(String username);

    CustomerProfileResponse updateProfile(
            String username,
            UpdateProfileRequest request
    );

    void changePassword(
            String username,
            ChangePasswordRequest request
    );
}