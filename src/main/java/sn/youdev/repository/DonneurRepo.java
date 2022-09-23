package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Donneur;

@Repository
public interface DonneurRepo extends JpaRepository<Donneur,String> {
}
