package by.vpolkhovsky.web.controller;

import by.vpolkhovsky.config.ApplicationProperties;
import by.vpolkhovsky.dto.EchoRequest;
import by.vpolkhovsky.dto.EchoResponse;
import by.vpolkhovsky.dto.IamResponse;
import by.vpolkhovsky.dto.JwtUserDetails;
import by.vpolkhovsky.mapper.UserMapper;
import by.vpolkhovsky.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final ApplicationProperties applicationProperties;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping("/iam")
    public ResponseEntity<IamResponse> iam(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        log.info("Application {}. User [username={}] use endpoint /api/iam", applicationProperties.getName(), jwtUserDetails.getUsername());
        return userRepository.findByUsername(jwtUserDetails.getUsername())
                .map(user -> new IamResponse(applicationProperties.getName(), userMapper.map(user)))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/echo")
    public ResponseEntity<EchoResponse> echo(@AuthenticationPrincipal JwtUserDetails jwtUserDetails, @RequestBody EchoRequest request) {
        log.info("Application {}. User [username={}] use endpoint /api/echo and post message \"{}\"", applicationProperties.getName(), jwtUserDetails.getUsername(), request.text());
        return userRepository.findByUsername(jwtUserDetails.getUsername())
                .map(user -> new EchoResponse(applicationProperties.getName(), userMapper.map(user), request.text()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
