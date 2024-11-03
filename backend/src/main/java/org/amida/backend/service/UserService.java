package org.amida.backend.service;

import lombok.RequiredArgsConstructor;
import org.amida.backend.exception.EmailAlreadyTakenException;
import org.amida.backend.exception.UsernameAlreadyExistsException;
import org.amida.backend.model.User;
import org.amida.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(User user){

        if (!userRepository.existsByUsername(user.getUsername())){
            throw new UsernameAlreadyExistsException(String.format("User with username %s is already exists",
                    user.getUsername()));
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyTakenException(String.format("Email %s is already taken",
                    user.getEmail()));
        }

        return userRepository.save(user);
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
