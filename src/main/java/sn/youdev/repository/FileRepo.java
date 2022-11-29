package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.File;

import java.util.Optional;
@Repository
public interface FileRepo extends JpaRepository<File,Long> {
    Optional<File> findByNom(String nom);
}
