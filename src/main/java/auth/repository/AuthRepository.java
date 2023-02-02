package auth.repository;

import auth.domain.AbstractBaseUser;

import java.util.Optional;

public interface AuthRepository<T extends AbstractBaseUser> {

    Optional<T> findById(Long id);

    Optional<T> findByUsername(String username);
}
