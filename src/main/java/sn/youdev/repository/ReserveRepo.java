package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Reserve;
import sn.youdev.model.serializable.ReserveId;

import java.util.List;

@Repository
public interface ReserveRepo extends JpaRepository<Reserve, ReserveId> {
    List<Reserve> findByBanqueNom(String nom);
    List<Reserve> findByGroupeSanguin_Groupe(String groupe);
    List<Reserve> findByGroupeSanguin_GroupeAndQuantityIsGreaterThanEqual(String groupe,int quantity);
}
