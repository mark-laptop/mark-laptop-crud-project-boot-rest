package ru.ndg.crudproject.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ndg.crudproject.dao.role.RoleDao;
import ru.ndg.crudproject.dto.RoleDto;
import ru.ndg.crudproject.model.Role;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleRestServiceImpl implements RoleRestService {

    private final RoleDao roleDao;

    @Autowired
    public RoleRestServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoleDto> getAllRoles() {
        return mapSetRoleToSetRoleDto(roleDao.getAllRoles());
    }

    @Transactional(readOnly = true)
    @Override
    public RoleDto getRoleById(Long id) {
        return mapRoleToRoleDto(roleDao.getRoleById(id));
    }

    @Override
    public RoleDto getRoleByName(String name) {
        return mapRoleToRoleDto(roleDao.getRoleByName(name));
    }

    private List<RoleDto> mapSetRoleToSetRoleDto(List<Role> roles) {
        return roles.stream().map(this::mapRoleToRoleDto).collect(Collectors.toList());
    }

    private RoleDto mapRoleToRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
