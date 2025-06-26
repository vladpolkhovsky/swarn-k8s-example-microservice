package by.vpolkhovsky.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/auth")
public class AuthController {

    @PostMapping("/api/login")
    public void login() {
        // Обработка делегируется фильтру
    }
}
