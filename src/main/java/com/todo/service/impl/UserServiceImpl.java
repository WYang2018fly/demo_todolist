package com.todo.service.impl;

import com.todo.constant.UserExceptionConstant;
import com.todo.dto.user.*;
import com.todo.entity.User;
import com.todo.exception.BizException;
import com.todo.repository.UserRepository;
import com.todo.service.UserService;
import com.todo.util.UUIDUtil;
import com.todo.util.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        BeanUtils.copyProperties(userCreateDTO, user);
        user.setId(UUIDUtil.generateUUID());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        user.setCreatedAt(System.currentTimeMillis());
        user.setUpdatedAt(System.currentTimeMillis());

        User userEntity = userRepository.save(user);
        return UserMapper.INSTANCE.userEntityToUserDTO(userEntity);
    }

    @Override
    public Boolean checkUsernameIsExisted(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Page<UserDTO> getAllUsersByPage(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserMapper.INSTANCE::userEntityToUserDTO);
    }

    @Override
    public Boolean checkUserRole(String username, String role) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BizException(UserExceptionConstant.USER_NOT_EXISTED));
        return Objects.equals(user.getRole(), role);
    }

    @Override
    public UserDTO findUserByRole(String role) {
        User userEntity = userRepository.findByRole(role).orElseThrow(() -> new BizException(UserExceptionConstant.USER_NOT_EXISTED));
        return UserMapper.INSTANCE.userEntityToUserDTO(userEntity);
    }

    @Override
    public UserDTO findUserById(String userId) {
        User userEntity = userRepository.findById(userId).orElseThrow(() -> new BizException(UserExceptionConstant.USER_NOT_EXISTED));
        return UserMapper.INSTANCE.userEntityToUserDTO(userEntity);
    }
}