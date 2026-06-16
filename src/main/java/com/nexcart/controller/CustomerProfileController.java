package com.nexcart.controller;

import com.nexcart.dto.profile.ChangePasswordRequest;
import com.nexcart.dto.profile.CustomerProfileResponse;
import com.nexcart.dto.profile.UpdateProfileRequest;
import com.nexcart.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/profile")
@RequiredArgsConstructor
public class CustomerProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<CustomerProfileResponse> getProfile(
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        return ResponseEntity.ok(
                profileService.getProfile(
                        userDetails.getUsername()
                )
        );
    }

    @PutMapping
    public ResponseEntity<CustomerProfileResponse> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdateProfileRequest request
    ) {

        return ResponseEntity.ok(
                profileService.updateProfile(
                        userDetails.getUsername(),
                        request
                )
        );
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ChangePasswordRequest request
    ) {

        profileService.changePassword(
                userDetails.getUsername(),
                request
        );

        return ResponseEntity.ok().build();
    }
}