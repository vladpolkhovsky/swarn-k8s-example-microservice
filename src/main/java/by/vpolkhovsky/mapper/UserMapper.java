package by.vpolkhovsky.mapper;

import by.vpolkhovsky.dto.JwtUserDetails;
import by.vpolkhovsky.dto.UserDto;
import by.vpolkhovsky.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map(User user);

    @Mapping(target = "authorities", source = ".", qualifiedByName = "emptyList")
    JwtUserDetails mapToDetails(User user);

    @Named("emptyList")
    default <T> List<T> emptyList(Object ignored) {
        return List.of();
    }
}
