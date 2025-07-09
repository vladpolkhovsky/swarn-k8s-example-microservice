package by.vpolkhovsky.services;

import by.vpolkhovsky.dto.JwtUserDetails;
import by.vpolkhovsky.mapper.UserMapper;
import by.vpolkhovsky.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(userMapper::mapToDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
