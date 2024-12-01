package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
