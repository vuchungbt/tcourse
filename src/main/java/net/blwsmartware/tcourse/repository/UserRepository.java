package net.blwsmartware.tcourse.repository;

import net.blwsmartware.tcourse.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.username = :value OR u.ggID = :value")
    Optional<User> findByUsernameOrGgID(@Param("value") String value);
    Optional<User> findByTel(String tel);
    List<User> findByRoles_NameIn(List<String> roleNames);
    Page<User> findByRoles_NameIn(List<String> roleNames, Pageable pageable);

}
