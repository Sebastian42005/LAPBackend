package org.example.buchibackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.buchibackend.domain.User;
import org.example.buchibackend.dto.AuthenticationRequest;
import org.example.buchibackend.dto.AuthenticationResponse;
import org.example.buchibackend.exception.WrongLoginCredentialsException;
import org.example.buchibackend.repository.UserRepository;
import org.example.buchibackend.security.ShaUtils;
import org.example.buchibackend.security.jwt.JwtTokenUtil;
import org.example.buchibackend.security.jwt.JwtUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil tokenUtil;
    private final UserRepository userRepository;

    @Transactional
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws WrongLoginCredentialsException {
        final UserDetails userDetails = userDetailsService.verifyUser(authenticationRequest.email(), ShaUtils.decode(authenticationRequest.password()));
        final String token = tokenUtil.generateToken(userDetails);
        return new AuthenticationResponse(token, userDetails.getAuthorities().stream().findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getAuthority());
    }

    @Transactional
    public User register(User user) {
        Optional<User> dbUser = userRepository.findByEmail(user.getEmail());
        if (dbUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        user.setPassword(ShaUtils.decode(user.getPassword()));
        userRepository.save(user);
        return user;
    }
}
