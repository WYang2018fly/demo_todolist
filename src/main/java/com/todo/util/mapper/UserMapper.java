package com.todo.util.mapper;

import com.todo.dto.user.UserDTO;
import com.todo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO userEntityToUserDTO(User user);
    User userDTOToUserEntity(UserDTO userDTO);
}