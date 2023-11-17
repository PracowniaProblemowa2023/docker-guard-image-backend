package pl.dockerguardimage.api.functionality.user.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dockerguardimage.security.functionality.user.context.UserContextHolder;
import pl.dockerguardimage.security.functionality.user.model.AuthenticatedUser;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public AuthenticatedUser getProfile() {
        return UserContextHolder.getAuthenticatedUser();
    }


}
