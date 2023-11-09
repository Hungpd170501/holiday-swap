package com.example.holidayswap.utils;

import static com.example.holidayswap.constants.ErrorMessage.DOES_NOT_BELONG_THIS_RESOURCE;
import static com.example.holidayswap.constants.ErrorMessage.USER_NOT_FOUND;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.holidayswap.domain.entity.auth.RoleName;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.exception.AccessDeniedException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;

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

    public Optional<User> GetUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User user) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    //this will return the resource is belongs to the membership
    public User isBelongToMember(Long userId) {
        User user = getAuthenticatedUser();
        if (!Objects.equals(user.getRole().getName(), RoleName.Membership.name()))
            throw new AccessDeniedException("Only Member can access this resource.");
        if (!Objects.equals(user.getUserId(), userId)) throw new AccessDeniedException(DOES_NOT_BELONG_THIS_RESOURCE);
        return user;
    }
}
