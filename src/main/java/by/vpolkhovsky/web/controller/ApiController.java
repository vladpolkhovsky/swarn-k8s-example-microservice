package by.vpolkhovsky.web.controller;

import by.vpolkhovsky.dto.EchoRequest;
import by.vpolkhovsky.dto.EchoResponse;
import by.vpolkhovsky.dto.IamResponse;
import by.vpolkhovsky.dto.JwtUserDetails;
import by.vpolkhovsky.mapper.UserMapper;
import by.vpolkhovsky.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/iam")
    public ResponseEntity<IamResponse> iam(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        return userRepository.findByUsername(jwtUserDetails.getUsername())
                .map(user -> new IamResponse(appName, userMapper.map(user)))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/echo")
    public ResponseEntity<EchoResponse> echo(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @RequestBody EchoRequest request) {
        log.info("User [username={}] post message \"{}\"", jwtUserDetails.getUsername(), request.text());
        return userRepository.findByUsername(jwtUserDetails.getUsername())
                .map(user -> new EchoResponse(appName, userMapper.map(user), request.text()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
