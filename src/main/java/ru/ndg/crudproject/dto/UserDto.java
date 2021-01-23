package ru.ndg.crudproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private Long id;

    private String nickname;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private Byte age;

    private Set<RoleDto> roles = new HashSet<>();
}
