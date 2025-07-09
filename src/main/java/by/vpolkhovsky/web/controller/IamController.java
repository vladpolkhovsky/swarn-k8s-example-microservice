package by.vpolkhovsky.web.controller;

import by.vpolkhovsky.dto.IamResponse;
import by.vpolkhovsky.dto.JwtUserDetails;
import by.vpolkhovsky.mapper.UserMapper;
import by.vpolkhovsky.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/api/iam")
@RequiredArgsConstructor
public class IamController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/")
    public ResponseEntity<IamResponse> iam(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        return userRepository.findByUsername(jwtUserDetails.getUsername())
                .map(user -> new IamResponse(appName, userMapper.map(user)))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
