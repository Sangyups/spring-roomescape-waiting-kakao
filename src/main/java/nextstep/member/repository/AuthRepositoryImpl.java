package nextstep.member.repository;

import auth.repository.AuthRepository;
import nextstep.member.dao.MemberDao;
import nextstep.member.domain.Member;
import nextstep.member.mapper.MemberMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthRepositoryImpl implements AuthRepository<Member> {

    private final MemberDao memberDao;

    public AuthRepositoryImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public Optional<Member> findById(Long id) {

        return Optional.ofNullable(MemberMapper.INSTANCE.entityToDomain(memberDao.findById(id)));
    }

    @Override
    public Optional<Member> findByUsername(String username) {

        return Optional.ofNullable(MemberMapper.INSTANCE.entityToDomain(memberDao.findByUsername(username)));
    }
}
