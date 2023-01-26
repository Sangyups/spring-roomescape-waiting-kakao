package nextstep.member.repository;

import auth.domain.UserDetails;
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
    public Long save(UserDetails userDetails) {

        return memberDao.save(MemberMapper.INSTANCE.userDetailsToEntity(userDetails));
    }

    @Override
    public Optional<UserDetails> findById(Long id) {

        return Optional.ofNullable(MemberMapper.INSTANCE.entityToUserDetails(memberDao.findById(id)));
    }

    @Override
    public Optional<UserDetails> findByUsername(String username) {

        return Optional.ofNullable(MemberMapper.INSTANCE.entityToUserDetails(memberDao.findByUsername(username)));
    }
}
