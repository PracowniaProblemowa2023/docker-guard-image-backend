package pl.dockerguardimage.security.utils;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakUtils {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public AccessTokenResponse generateAccessToken(String username, String password) {
        return Keycloak
                .getInstance(serverUrl, realm, username, password, clientId, clientSecret)
                .tokenManager()
                .grantToken();
    }

}
