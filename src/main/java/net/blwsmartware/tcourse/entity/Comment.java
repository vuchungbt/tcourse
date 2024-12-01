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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence_name", initialValue = 1000, allocationSize = 1)
    long id;

    @Column(columnDefinition = "TEXT")
    String content;

    int vote;
    @CreationTimestamp
    @Column(name = "create_at")
    Instant createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    Instant updateAt;
    
    int status;

    @ManyToOne
    User created;

}
