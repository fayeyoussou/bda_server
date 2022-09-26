package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Donneur;

import java.util.List;

@Repository
public interface DonneurRepo extends JpaRepository<Donneur,String> {
    List<Donneur> findAllByActiveTrueOrderByInfoPerso_Nom();
}
