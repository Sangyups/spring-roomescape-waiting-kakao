package nextstep.member.repository;

import auth.domain.AbstractUser;
import auth.repository.MemberRepository;
import nextstep.member.dao.MemberDao;
import nextstep.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberDao memberDao;

    @Autowired
    public MemberRepositoryImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public Long save(AbstractUser userDetails) {

        return memberDao.save(MemberMapper.INSTANCE.abstractUserToEntity(userDetails));
    }

    @Override
    public Optional<AbstractUser> findById(Long id) {

        return Optional.ofNullable(MemberMapper.INSTANCE.entityToAbstractUser(memberDao.findById(id)));
    }

    @Override
    public Optional<AbstractUser> findByUsername(String username) {

        return Optional.ofNullable(MemberMapper.INSTANCE.entityToAbstractUser(memberDao.findByUsername(username)));
    }
}
