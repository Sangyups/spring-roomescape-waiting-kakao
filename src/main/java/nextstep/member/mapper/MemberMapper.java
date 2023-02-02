package nextstep.member.mapper;

import auth.domain.AbstractUser;
import auth.domain.Role;
import nextstep.member.domain.Member;
import nextstep.member.dto.MemberRequest;
import nextstep.member.dto.MemberResponse;
import nextstep.member.entity.MemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    default MemberResponse domainToResponseDto(Member member) {
        if (member == null) {
            return null;
        }

        Long id = member.getId();
        String username = member.getUsername();
        String password = member.getPassword();
        String name = member.getName();
        String phone = member.getPhone();
        String role = member.getRole().name();

        return new MemberResponse(id, username, password, name, phone, role);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", qualifiedByName = "roleMapper")
    Member requestDtoToDomain(MemberRequest memberRequest);

    Member abstractUserToDomain(AbstractUser abstractUser);

    MemberEntity abstractUserToEntity(AbstractUser abstractUser);

    @Mapping(target = "role", qualifiedByName = "roleMapper")
    Member entityToDomain(MemberEntity memberEntity);

    @Named("roleMapper")
    default Role stringToEnumRole(String role) {
        try {
            return Role.of(role);
        } catch (NullPointerException e) {
            return Role.NONE;
        }
    }
}
