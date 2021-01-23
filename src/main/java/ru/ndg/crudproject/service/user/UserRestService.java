package ru.ndg.crudproject.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ndg.crudproject.dto.UserDto;
import ru.ndg.crudproject.model.User;

import java.util.List;

public interface UserRestService extends UserDetailsService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto saveUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
    void deleteUser(Long id);
    UserDto getUserByUsername(String username);
}
