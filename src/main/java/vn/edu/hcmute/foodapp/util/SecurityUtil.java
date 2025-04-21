package vn.edu.hcmute.foodapp.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.hcmute.foodapp.entity.User;

@Service
@Slf4j
public class SecurityUtil {
    public static User getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            log.info("user id : {}", user.getId().toString());
            return user;
        }
        catch (Exception e){
            log.error("Error fetching user from security context: {}", e.getMessage());
            throw new AccessDeniedException("Access denied");
        }
    }
}
