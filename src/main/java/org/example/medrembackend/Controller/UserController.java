package org.example.medrembackend.Controller;

import org.example.medrembackend.DTOs.LoginRequest;
import org.example.medrembackend.DTOs.RegistrationRequest;
import org.example.medrembackend.DTOs.RegistrationResponse;
import org.example.medrembackend.Entity.UserEntity;
import org.example.medrembackend.Service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImp userService;

    @Autowired
    UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody RegistrationRequest registrationReq) {
        RegistrationResponse response = userService.registerUser(registrationReq);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public List<UserEntity> getAllUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest req) {

        return ResponseEntity.ok(userService.loginUser(req));
    }

}
