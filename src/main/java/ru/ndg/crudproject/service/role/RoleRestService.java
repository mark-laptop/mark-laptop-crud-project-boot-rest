package ru.ndg.crudproject.service.role;

import ru.ndg.crudproject.dto.RoleDto;

import java.util.List;

public interface RoleRestService {
    List<RoleDto> getAllRoles();
    RoleDto getRoleById(Long id);
    RoleDto getRoleByName(String name);
}
