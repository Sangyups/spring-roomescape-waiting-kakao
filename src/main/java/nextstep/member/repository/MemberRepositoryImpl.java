package nextstep.member.repository;

import nextstep.member.dao.MemberDao;
import nextstep.member.domain.Member;
import nextstep.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberDao memberDao;

    @Autowired
    public MemberRepositoryImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public Long save(Member userDetails) {

        return memberDao.save(MemberMapper.INSTANCE.abstractUserToEntity(userDetails));
    }
}
