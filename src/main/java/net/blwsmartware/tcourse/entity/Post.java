package net.blwsmartware.tcourse.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
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
    User created;

    @ManyToMany
    Set<Tag> tag ;

    @ManyToMany
    Set<Category> categories ;

    @OneToMany
    List<Comment> comments ;

    @OneToMany
    List<Section> sections ;

    @OneToMany
    Set<Skill> skills ;

    @OneToMany
    Set<Discount> discounts ;

    @OneToMany
    Set<Vote> votes;

}
