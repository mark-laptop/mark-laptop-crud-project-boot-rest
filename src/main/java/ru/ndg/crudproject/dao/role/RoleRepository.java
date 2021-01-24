package ru.ndg.crudproject.dao.role;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ndg.crudproject.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
