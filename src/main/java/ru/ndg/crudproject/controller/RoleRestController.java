package ru.ndg.crudproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ndg.crudproject.dto.RoleDto;
import ru.ndg.crudproject.service.role.RoleRestService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class RoleRestController {

    private final RoleRestService roleRestService;

    @Autowired
    public RoleRestController(RoleRestService roleRestService) {
        this.roleRestService = roleRestService;
    }

    @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return new ResponseEntity<>(roleRestService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping(value = "/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> getRoleById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(roleRestService.getRoleById(id), HttpStatus.OK);
    }
}
