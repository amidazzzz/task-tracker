package org.amida.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amida.backend.config.UserDetailsImpl;
import org.amida.backend.exception.EmailAlreadyTakenException;
import org.amida.backend.exception.InvalidCredentialsException;
import org.amida.backend.exception.UsernameAlreadyExistsException;
import org.amida.backend.model.User;
import org.amida.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void registerUser(User user){

        if (userRepository.existsByUsername(user.getUsername())){ // fix existsByUsername condition
            throw new UsernameAlreadyExistsException(String.format("User with username %s is already exists",
                    user.getUsername()));
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyTakenException(String.format("Email %s is already taken",
                    user.getEmail()));
        }

        var password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public String authUser(String username, String password){
        log.info("User Service username {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username %s not found",
                        username)));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        String token = jwtService.generateToken(new UserDetailsImpl(user));
        log.info("Generated token {}", token);
        return token;
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
