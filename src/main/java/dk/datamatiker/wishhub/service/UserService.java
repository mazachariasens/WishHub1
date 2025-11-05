package dk.datamatiker.wishhub.service;

import dk.datamatiker.wishhub.model.User;
import dk.datamatiker.wishhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ===== Opret bruger =====
    public Long opretBruger(String name, String email, String password) {
        User user = new User(name, email, password);
        userRepository.save(user);
        return user.getId();
    }

    // ===== Login =====
    public Long login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) { // simpelt, kan krypteres senere
                return user.getId();
            }
        }
        return null;
    }

    // ===== Find bruger efter ID =====
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
