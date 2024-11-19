package net.blwsmartware.tcourse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Size(min = 6,message = "PASSWORD_MUST_6_DIGITS")
    String password;

    @NotNull(message = "NAME_NOT_NULL")
    String name;

    @Email(message = "EMAIL_INVALID")
    @Column(unique = true)
    String email;

    @Builder.Default
    boolean isActive=true;

    @Builder.Default
    boolean emailVerified=false;

    @CreationTimestamp
    @Column(name = "create_at")
    Instant createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    Instant updateAt;

    @Column(name = "cover_photo")
    String coverPhoto ;

    String avatar, title, description,intro,tel , fbID, ggID;

    LocalDate dob;

    @ManyToMany
    Set<Role> roles;

}
