package io.convocli.controller;

import io.convocli.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepo;
    public UserController(UserRepository userRepo) { this.userRepo = userRepo; }

    @GetMapping("/search")
    public List<String> search(@RequestParam String q) {
        return userRepo.findAll().stream()
                .map(u -> u.getUsername())
                .filter(name -> name.contains(q))
                .collect(Collectors.toList());
    }
}
