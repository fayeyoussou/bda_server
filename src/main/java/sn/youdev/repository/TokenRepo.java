package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token,Long> {
    Optional<Token> findByCode(String code);
    List<Token> findAllByUserLogin(String login);
    List<Token> findAllByUser_InfoPerso_Email(String email);
}
