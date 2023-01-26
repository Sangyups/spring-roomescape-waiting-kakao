package auth.repository;

import auth.domain.UserDetails;

import java.util.Optional;

public interface MemberRepository {

    Long save(UserDetails userDetails);

    Optional<UserDetails> findById(Long id);

    Optional<UserDetails> findByUsername(String username);
}
