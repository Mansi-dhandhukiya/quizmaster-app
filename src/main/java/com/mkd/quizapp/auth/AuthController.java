package com.mkd.quizapp.auth;

import com.mkd.quizapp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Hardcoded admin for now — Step 7 moves this to DB
    private final String ADMIN_USER = "admin";
    private final String ADMIN_PASS_HASH =
        "$2a$10$nADXo7SJ2AzcsFWZoyjhf.SEHkSFQOmIU4TfRBNjniAjrqG3tr1aq";
        // This is bcrypt of "1234"

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        if (!request.getUsername().equals(ADMIN_USER)) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        if (!passwordEncoder.matches(request.getPassword(), ADMIN_PASS_HASH)) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }
}