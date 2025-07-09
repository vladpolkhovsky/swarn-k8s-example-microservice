package by.vpolkhovsky.web.controller;

import by.vpolkhovsky.dto.RegisterRequestDto;
import by.vpolkhovsky.entity.User;
import by.vpolkhovsky.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth") // Фиксим базовый путь
public class AuthController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * @see by.vpolkhovsky.web.filter.JwtAuthenticationFilter
     */
    @PostMapping("/api/login")
    public void login() {

    }

    @PostMapping("/api/register")
    @Transactional
    public ResponseEntity<Void> register(@RequestBody RegisterRequestDto registerRequestDto) {
        var user = new User(null, registerRequestDto.username(), passwordEncoder.encode(registerRequestDto.password()));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
