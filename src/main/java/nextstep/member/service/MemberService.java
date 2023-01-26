package nextstep.member.service;

import auth.domain.UserDetails;
import auth.repository.MemberRepository;
import nextstep.member.domain.Member;
import nextstep.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long create(Member member) {
        UserDetails requestedMember = MemberMapper.INSTANCE.domainToUserDetails(member);
        requestedMember.encryptPassword();

        return memberRepository.save(requestedMember);
    }

    public Optional<Member> findById(Long id) {

        return memberRepository.findById(id)
                .map(MemberMapper.INSTANCE::userDetailsToDomain)
                ;
    }
}
