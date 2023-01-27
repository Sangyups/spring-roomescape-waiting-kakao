package auth.repository;

import auth.domain.AbstractUser;

import java.util.Optional;

public interface MemberRepository {

    Long save(AbstractUser userDetails);

    Optional<AbstractUser> findById(Long id);

    Optional<AbstractUser> findByUsername(String username);
}
