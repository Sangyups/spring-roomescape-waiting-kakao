package auth.service;

import auth.domain.AbstractUser;
import auth.domain.AccessToken;
import auth.exception.UnauthenticatedException;
import auth.exception.UserNotFoundException;
import auth.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberRepository memberRepository;

    @Autowired
    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public AccessToken login(String username, String password) {
        AbstractUser requestedUser = AbstractUser.builder()
                .username(username)
                .password(password)
                .build();

        AbstractUser existUser = memberRepository.findByUsername(requestedUser.getUsername())
                .orElseThrow(UserNotFoundException::new);
        if (!existUser.matchPassword(requestedUser)) {
            throw new UnauthenticatedException();
        }

        return AccessToken.create(String.valueOf(existUser.getId()), existUser.getRole().name());
    }
}
