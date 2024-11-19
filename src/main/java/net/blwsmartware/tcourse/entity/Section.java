package net.blwsmartware.tcourse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull(message = "NAME_NOT_NULL")
    String name;

    String description;
    String content;
    String type;
    String source;
    String thumbnail;
    LocalTime time;

    @Column(name = "item_order")
    int itemOrder;

    @OneToMany
    Set<Item> items;

}
