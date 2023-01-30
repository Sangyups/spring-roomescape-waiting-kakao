package nextstep.member.repository;

import nextstep.member.domain.Member;

import java.util.Optional;

public interface MemberRepository {

    Long save(Member member);

    Optional<Member> findById(Long id);
}
