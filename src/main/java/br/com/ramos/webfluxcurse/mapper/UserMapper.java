package br.com.ramos.webfluxcurse.mapper;

import br.com.ramos.webfluxcurse.entity.User;
import br.com.ramos.webfluxcurse.model.request.UserRequest;
import br.com.ramos.webfluxcurse.model.response.UserResponse;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(final UserRequest userRequest);

    @Mapping(target = "id", ignore = true)
    User toEntity(final UserRequest userRequest, @MappingTarget final User user);

    UserResponse toRespose(final User entity);
}
