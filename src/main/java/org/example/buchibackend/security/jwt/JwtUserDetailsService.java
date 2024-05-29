package org.example.buchibackend.security.jwt;

import lombok.RequiredArgsConstructor;
import org.example.buchibackend.exception.WrongLoginCredentialsException;
import org.example.buchibackend.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) {
        org.example.buchibackend.domain.User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name()).build();
    }

    public UserDetails verifyUser(String username, String password) throws WrongLoginCredentialsException {
        org.example.buchibackend.domain.User user = userRepository.login(username, password).orElseThrow(WrongLoginCredentialsException::new);
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name()).build();
    }
}
