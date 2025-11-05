package dk.datamatiker.wishhub.repository;

import dk.datamatiker.wishhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Returnerer nu en Optional<User>
    Optional<User> findByEmail(String email);
}
