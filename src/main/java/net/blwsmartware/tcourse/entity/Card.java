package net.blwsmartware.tcourse.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    long id;

    String number;

    String  expr;

    int CVV;

    String name, address;

    boolean isDefault;

}
