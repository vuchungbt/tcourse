package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.ImageStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageStorage, Long> {
    Optional<ImageStorage> findByName(String name);
}
