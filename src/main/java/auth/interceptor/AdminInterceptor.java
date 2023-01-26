package auth.interceptor;

import auth.domain.AccessToken;
import auth.exception.UnauthenticatedException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        AccessToken accessToken = AccessToken.from(extractToken(authorization));

        String role = accessToken.getRole();
        if (!Objects.equals(role, "ADMIN")) {
            throw new UnauthenticatedException();
        }

        return true;
    }

    private String extractToken(String authorization) {
        return Optional.ofNullable(authorization)
                .map(v -> v.split("Bearer ")[1])
                .orElseThrow();
    }
}
