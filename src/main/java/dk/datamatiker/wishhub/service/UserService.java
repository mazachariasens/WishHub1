package dk.datamatiker.wishhub.service;

import dk.datamatiker.wishhub.model.User;
import dk.datamatiker.wishhub.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Autowired
    private UserRepo userRepository;

    // ===== Opret bruger =====
//    public Long opretBruger(String name, String email, String password) {
//        User user = new User(name, email, password);
//        userRepository.save(user);
//        return user.getId();
//    }

    public User opretBruger(String name, String email, String password) {
        User user = new User(name, email, password);
        userRepository.save(user);
        return user;
    }

    // ===== Login =====
//    public Long login(String email, String password) {
//        Optional<User> optionalUser = userRepository.findByEmail(email);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            if (user.getPassword().equals(password)) { // simpelt, kan krypteres senere
//                return user.getId();
//            }
//        }
//        return null;
//    }

    public User login(String email, String password) {
        User user = userRepo.findUserByEmail(email);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    // ===== Find bruger efter ID =====
//    public User findById(Long id) {
//        return userRepository.findById(id).orElse(null);
//    }

    public User findById(int id) {
        return userRepo.findUserById(id);
    }
}
