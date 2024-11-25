package net.blwsmartware.tcourse.dto.request.post;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SectionForm {
    private List<SectionRequest> components;

    public List<SectionRequest> getComponents() {
        return components;
    }

    public void setComponents(List<SectionRequest> components) {
        this.components = components;
    }


}

