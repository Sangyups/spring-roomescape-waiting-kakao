package auth.service;

import auth.domain.AccessToken;
import auth.domain.UserDetails;
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
        UserDetails requestedUser = UserDetails.builder()
                .username(username)
                .password(password)
                .build();

        UserDetails existUser = memberRepository.findByUsername(requestedUser.getUsername())
                .orElseThrow(UserNotFoundException::new);
        if (!existUser.matchPassword(requestedUser)) {
            throw new UnauthenticatedException();
        }

        return AccessToken.create(String.valueOf(existUser.getId()), existUser.getRole());
    }
}
