package auth.controller;

import auth.domain.AccessToken;
import auth.dto.AccessTokenResponse;
import auth.dto.LoginRequest;
import auth.mapper.AuthMapper;
import auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        AccessToken accessToken = authService.login(loginRequest.getUsername(), loginRequest.getPassword());

        return ResponseEntity.ok(AuthMapper.INSTANCE.accessTokenDomainToDto(accessToken));
    }
}
