package com.example.holidayswap.utils;

import com.example.holidayswap.domain.entity.auth.RoleName;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.exception.AccessDeniedException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.example.holidayswap.constants.ErrorMessage.DOES_NOT_BELONG_THIS_RESOURCE;
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

    //this will return the resource is belongs to the membership
    public User isBelongToMember(Long userId) {
        User user = getAuthenticatedUser();
        if (!Objects.equals(user.getRole().getName(), RoleName.Membership.name()))
            throw new AccessDeniedException("Only Member can access this resource.");
        if (!Objects.equals(user.getUserId(), userId)) throw new AccessDeniedException(DOES_NOT_BELONG_THIS_RESOURCE);
        return user;
    }
}
