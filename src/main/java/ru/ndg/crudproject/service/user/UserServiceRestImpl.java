package ru.ndg.crudproject.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ndg.crudproject.dao.role.RoleDao;
import ru.ndg.crudproject.dao.user.UserDao;
import ru.ndg.crudproject.dto.RoleDto;
import ru.ndg.crudproject.dto.UserDto;
import ru.ndg.crudproject.model.Role;
import ru.ndg.crudproject.model.User;
import ru.ndg.crudproject.util.SecurityUtil;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service(value = "userRestService")
public class UserServiceRestImpl implements UserRestService {

    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceRestImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = userDao.getAllUsers();
        return mapListUserToListUserDto(allUsers);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserById(Long id) {
        return mapUserToUserDto(userDao.getUserById(id));
    }

    @Transactional
    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = mapUserDtoToUser(userDto);
        return mapUserToUserDto(userDao.saveUser(user));
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto) {
        User updateUser = userDao.updateUser(mapUserDtoToUser(userDto));
        SecurityUtil.refreshRolesForAuthenticatedUser(updateUser);
        return mapUserToUserDto(updateUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserByUsername(String username) {
        return mapUserToUserDto(userDao.getUserByUsername(username));
    }

    private List<UserDto> mapListUserToListUserDto(List<User> users) {
        return users.stream().map(user -> UserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .email(user.getEmail())
                .roles(mapSetRoleToSetRoleDto(user.getRoles()))
                .build()).collect(Collectors.toList());
    }

    private Set<RoleDto> mapSetRoleToSetRoleDto(Set<Role> roles) {
        return roles.stream().map(role -> RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build()).collect(Collectors.toSet());
    }

    private Set<Role> mapSetRoleDtoToSetRole(Set<RoleDto> rolesDtos) {
        return rolesDtos.stream().map(role -> Role.builder()
                .id(role.getId())
                .name(role.getName())
                .build()).collect(Collectors.toSet());
    }

    private UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .age(user.getAge())
                .email(user.getEmail())
                .roles(mapSetRoleToSetRoleDto(user.getRoles()))
                .build();
    }

    private User mapUserDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .nickname(userDto.getNickname())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(userDto.getPassword())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .roles(mapSetRoleDtoToSetRole(userDto.getRoles()))
                .build();
    }
}
