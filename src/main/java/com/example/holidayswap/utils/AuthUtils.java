package com.example.holidayswap.utils;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.example.holidayswap.constants.ErrorMessage.USER_NOT_FOUND;

@Component
public class AuthUtils {
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User user) {
                return user;
            }
        }
        throw new EntityNotFoundException(USER_NOT_FOUND);
    }
}
