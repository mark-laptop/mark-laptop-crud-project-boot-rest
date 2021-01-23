package ru.ndg.crudproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoleDto {

    @NotNull(message = "not be empty")
    private Long id;

    @NotNull(message = "not be empty")
    private String name;
}
