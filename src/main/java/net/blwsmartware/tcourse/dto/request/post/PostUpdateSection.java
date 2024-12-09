package net.blwsmartware.tcourse.dto.request.post;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.blwsmartware.tcourse.entity.Category;
import net.blwsmartware.tcourse.entity.Section;
import net.blwsmartware.tcourse.entity.Skill;

import java.time.Instant;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostUpdateSection {

    Set<Section> sections ;

    Set<Category> categories ;
}
