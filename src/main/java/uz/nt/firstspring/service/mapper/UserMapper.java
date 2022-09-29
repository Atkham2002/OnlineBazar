package uz.nt.firstspring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uz.nt.firstspring.dto.UserInfoDto;
import uz.nt.firstspring.entity.Authorities;
import uz.nt.firstspring.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserInfoDto dto);

    default SimpleGrantedAuthority convert(Authorities auth){
        return new SimpleGrantedAuthority(auth.getName());
    }

//    @Mapping(target = "permissions", source = "authorities")
    UserInfoDto toDto(User user);
}
