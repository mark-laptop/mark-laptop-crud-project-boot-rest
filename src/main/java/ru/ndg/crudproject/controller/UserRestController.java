package ru.ndg.crudproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ndg.crudproject.dto.UserDto;
import ru.ndg.crudproject.service.user.UserRestService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/")
public class UserRestController {

    private final UserRestService userRestService;

    @Autowired
    public UserRestController(UserRestService userRestService) {
        this.userRestService = userRestService;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userRestService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(userRestService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/user/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userRestService.saveUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping(value = "/user/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userRestService.updateUser(userDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/delete/{id}")
    public void deleteUser(@PathVariable(name = "id") Long id) {
        userRestService.deleteUser(id);
    }
}
