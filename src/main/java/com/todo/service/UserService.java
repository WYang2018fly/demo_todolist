package com.todo.service;

import com.todo.dto.user.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface UserService {
    UserDTO createUser(UserCreateDTO userCreateDTO);

    Boolean checkUsernameIsExisted(String username);

    Boolean checkUserRole(String username, String role);

    Page<UserDTO> getAllUsersByPage(Pageable pageable);

    UserDTO findUserByRole(String role);
}
