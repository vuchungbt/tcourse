package net.blwsmartware.tcourse.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
    @SequenceGenerator(name = "post_sequence", sequenceName = "post_sequence_name", initialValue = 10010, allocationSize = 1)
    Long id ;

    @CreationTimestamp
    @Column(name = "create_at")
    Instant createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    Instant updateAt;

    int price, discount;

    Instant publishedAt;

    @Column(name = "cover_photo")
    String  coverPhoto;

    @Column(columnDefinition = "TEXT")
    String  created_by, description, name ,
            thumbnail ,
            title ,
            content ;
    int status ;

    @ManyToOne
    @JoinColumn(name = "created_id", nullable = false)
    User created;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Section> sections;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Skill> skills;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Discount> discounts;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Vote> votes;

    @ManyToMany(cascade = CascadeType.PERSIST)
    Set<Category> categories;

}
