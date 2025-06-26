package by.vpolkhovsky.services;

import by.vpolkhovsky.dto.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new JwtUserDetails("admin", "123", List.of());
    }
}
