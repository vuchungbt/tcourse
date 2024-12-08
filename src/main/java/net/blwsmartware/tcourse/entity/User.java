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
import java.util.List;
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

    @Column(columnDefinition = "TEXT")
    String avatar, title, description,intro,tel , fbID, ggID;

    String password;

    @NotNull(message = "NAME_NOT_NULL")
    String name;

    @Column(unique = true)
    String username;

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

    LocalDate dob;

    @ManyToMany
    Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Card> cards;

    @OneToMany(mappedBy = "created", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Invoice> invoices;

}
