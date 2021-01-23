package ru.ndg.crudproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ndg.crudproject.dto.validate.UserCreate;
import ru.ndg.crudproject.dto.validate.UserUpdate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    @NotNull(groups = {UserUpdate.class})
    private Long id;

    @NotEmpty(groups = {UserCreate.class, UserUpdate.class})
    private String nickname;

    @NotEmpty(groups = {UserCreate.class, UserUpdate.class})
    private String firstName;

    @NotEmpty(groups = {UserCreate.class, UserUpdate.class})
    private String lastName;

    @NotEmpty(groups = {UserCreate.class, UserUpdate.class})
    private String password;

    @NotEmpty(groups = {UserCreate.class, UserUpdate.class})
    private String email;

    private Byte age;

    @NotEmpty(groups = {UserCreate.class, UserUpdate.class})
    private Set<RoleDto> roles = new HashSet<>();
}
