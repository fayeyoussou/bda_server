package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByLoginOrInfoPerso_Email(String login, String email);
    Boolean existsByLogin(String login);

    Boolean existsByInfoPerso_Email(String email);
    List<User> findAllByNonLockedTrue();
}
