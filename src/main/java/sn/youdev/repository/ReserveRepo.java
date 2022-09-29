package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Banque;
import sn.youdev.model.Reserve;
import sn.youdev.model.serializable.ReserveId;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReserveRepo extends JpaRepository<Reserve, ReserveId> {
    List<Reserve> findByBanque(Banque banque);
    List<Reserve> findByGroupeSanguin_Groupe(String groupe);
    Optional<Reserve> findByGroupeSanguin_GroupeAndBanque(String groupe,Banque banque);
    List<Reserve> findByGroupeSanguin_GroupeAndQuantityIsGreaterThanEqual(String groupe,int quantity);
}
