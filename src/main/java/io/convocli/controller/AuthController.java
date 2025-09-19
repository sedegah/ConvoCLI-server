package io.convocli.controller;

import io.convocli.model.User;
import io.convocli.repository.UserRepository;
import io.convocli.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String,String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (username == null || password == null) return ResponseEntity.badRequest().body("username/password required");
        if (userRepo.findByUsername(username).isPresent()) return ResponseEntity.badRequest().body("user exists");
        User u = new User(username, passwordEncoder.encode(password));
        userRepo.save(u);
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (username == null || password == null) return ResponseEntity.badRequest().body("username/password required");
        return userRepo.findByUsername(username)
                .filter(u -> passwordEncoder.matches(password, u.getPasswordHash()))
                .map(u -> ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(username))))
                .orElse(ResponseEntity.status(401).body("invalid credentials"));
    }
}
