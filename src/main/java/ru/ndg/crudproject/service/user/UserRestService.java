package ru.ndg.crudproject.service.user;

import ru.ndg.crudproject.dto.UserDto;

import java.util.List;

public interface UserRestService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto saveUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
    void deleteUser(Long id);
    UserDto getUserByUsername(String username);
}
