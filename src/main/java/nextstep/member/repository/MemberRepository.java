package nextstep.member.repository;

import java.util.Optional;
import nextstep.member.domain.Member;

public interface MemberRepository {

    Long save(Member member);

    Optional<Member> findById(Long id);
}
