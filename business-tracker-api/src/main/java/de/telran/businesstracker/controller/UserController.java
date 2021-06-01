package de.telran.businesstracker.controller;

import de.telran.businesstracker.data.User;
import de.telran.businesstracker.dto.UserDto;
import de.telran.businesstracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/users")
@Transactional
public class UserController {

    UserService userService;

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDto userDto) throws URISyntaxException {
        User result = userService.add();
        return ResponseEntity
                .created(new URI("/api/users/" + result.getId()))
                .body(result);
    }
}
