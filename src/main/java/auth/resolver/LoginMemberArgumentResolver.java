package auth.resolver;

import auth.annotation.AuthRequired;
import auth.domain.AbstractBaseUser;
import auth.domain.AccessToken;
import auth.exception.UnauthenticatedException;
import auth.repository.AuthRepository;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthRepository<? extends AbstractBaseUser> authRepository;

    public LoginMemberArgumentResolver(AuthRepository<? extends AbstractBaseUser> authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(AuthRequired.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String token = extractToken(authorization);

        AccessToken accessToken = AccessToken.from(token);
        if (!accessToken.isValid()) {
            throw new UnauthenticatedException();
        }

        return authRepository
                .findById(Long.parseLong(accessToken.getSub()))
                .orElseThrow(UnauthenticatedException::new)
                ;
    }

    private String extractToken(String authorization) {
        return Optional.ofNullable(authorization)
                .map(v -> v.split("Bearer ")[1])
                .orElseThrow(UnauthenticatedException::new);
    }
}