package auth.service;

import auth.domain.AbstractBaseUser;
import auth.domain.AbstractUser;
import auth.domain.AccessToken;
import auth.domain.Role;
import auth.exception.UnauthenticatedException;
import auth.exception.UserNotFoundException;
import auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthRepository<? extends AbstractBaseUser> authRepository;

    @Autowired
    public AuthService(AuthRepository<? extends AbstractBaseUser> authRepository) {
        this.authRepository = authRepository;
    }

    public AccessToken login(String username, String password) {
        AbstractUser requestedUser = AbstractUser.builder()
                .username(username)
                .password(password)
                .build();

        var existUser = authRepository.findByUsername(requestedUser.getUsername())
                .orElseThrow(UserNotFoundException::new);
        if (!existUser.matchPassword(requestedUser)) {
            throw new UnauthenticatedException();
        }

        Role role = Role.USER;
        if (existUser instanceof AbstractUser) {
            role = ((AbstractUser) existUser).getRole();
        }

        return AccessToken.create(String.valueOf(existUser.getId()), role);
    }
}
