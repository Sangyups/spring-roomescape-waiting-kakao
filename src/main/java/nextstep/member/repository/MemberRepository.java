package nextstep.member.repository;

import nextstep.member.domain.Member;

public interface MemberRepository {

    Long save(Member member);
}
