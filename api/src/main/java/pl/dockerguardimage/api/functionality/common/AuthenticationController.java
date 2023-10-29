package pl.dockerguardimage.api.functionality.common;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dockerguardimage.api.functionality.common.dto.LoginDto;
import pl.dockerguardimage.security.utils.KeycloakUtils;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final KeycloakUtils keycloakUtils;

    public AuthenticationController(KeycloakUtils keycloakUtils) {
        this.keycloakUtils = keycloakUtils;
    }

    @PostMapping("/token")
    public ResponseEntity<AccessTokenResponse> generateToken(@RequestBody LoginDto loginDto){
        AccessTokenResponse token = keycloakUtils.generateAccessToken(loginDto.username(), loginDto.password());
        return ResponseEntity.ok(token);
    }

}
