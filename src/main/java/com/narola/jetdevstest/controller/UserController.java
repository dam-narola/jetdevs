package com.narola.jetdevstest.controller;

import com.narola.jetdevstest.dto.UserDTO;
import com.narola.jetdevstest.model.UserEntity;
import com.narola.jetdevstest.repository.UsersRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UsersRepo usersRepo;

    public UserController(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String addUser(@RequestBody UserDTO userDTO) {
        String pass = userDTO.getPassword();
        BCryptPasswordEncoder passwordEncoder =
                new BCryptPasswordEncoder();
        if (!usersRepo.existsByUsername(userDTO.getName())) {
            UserEntity save = usersRepo.save(new UserEntity(null,
                    userDTO.getName(), passwordEncoder.encode(pass), userDTO.getRole()));
            return "User saved with ID:: " + save.getUserId();
        }
        return "User already exists!!!";
    }

//    @DeleteMapping("/{userId}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String deleteUser(@PathVariable String userId) {
//        if (!usersRepo.existsById(userId))
//            return "User does not exist!!!";
//        usersRepo.deleteById(userId);
//        return "User deleted with ID:: " + userId;
//    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Iterable<UserDTO> getAllUsers() {
        return usersRepo.findAll().stream()
                .map(userEntity -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setUserId(userEntity.getUserId());
                    userDTO.setName(userEntity.getUsername());
                    userDTO.setPassword(userEntity.getPassword());
                    userDTO.setRole(userEntity.getRole());
                    return userDTO;
                })
                .collect(Collectors.toList());
    }

}
