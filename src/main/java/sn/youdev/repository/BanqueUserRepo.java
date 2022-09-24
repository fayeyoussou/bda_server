package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Banque;
import sn.youdev.model.BanqueUser;
import sn.youdev.model.User;
import sn.youdev.model.serializable.BanqueUserId;

import java.util.List;
import java.util.Optional;
@Repository
public interface BanqueUserRepo extends JpaRepository<BanqueUser, BanqueUserId> {
    List<BanqueUser> findByBanque(Banque banque);
    Optional<BanqueUser> findByUser(User user);
}
