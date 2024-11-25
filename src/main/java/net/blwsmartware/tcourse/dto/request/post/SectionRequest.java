package net.blwsmartware.tcourse.dto.request.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionRequest {
    private int itemOrder;
    private String content;
    private String name;
    private String description;

}