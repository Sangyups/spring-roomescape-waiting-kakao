package nextstep.member.service;

import auth.repository.AuthRepository;
import nextstep.member.domain.Member;
import nextstep.member.mapper.MemberMapper;
import nextstep.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long create(Member member) {
        member.encryptPassword();

        return memberRepository.save(member);
    }

    public Optional<Member> findById(Long id) {

        return memberRepository.findById(id)
                .map(MemberMapper.INSTANCE::abstractUserToDomain)
                ;
    }
}
