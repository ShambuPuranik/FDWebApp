package com.finace.AuthService.mapper;



import com.finace.AuthService.domain.User;
import com.finace.AuthService.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User entity);

    User toEntity(UserDTO dto);

    List<UserDTO> toDtoList(List<User> entities);

    List<User> toEntityList(List<UserDTO> dtoList);
    void updateEnrolmentFromDTO(UserDTO dto, @MappingTarget User entity);
}
