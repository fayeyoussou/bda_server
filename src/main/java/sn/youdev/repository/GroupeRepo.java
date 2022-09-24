package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.GroupeSanguin;

@Repository
public interface GroupeRepo extends JpaRepository<GroupeSanguin,String> {
}
