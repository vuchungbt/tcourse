package net.blwsmartware.tcourse.mapper;

import net.blwsmartware.tcourse.dto.request.post.CategoryRequest;
import net.blwsmartware.tcourse.dto.request.post.CategoryUpdate;
import net.blwsmartware.tcourse.dto.response.post.CategoryResponse;
import net.blwsmartware.tcourse.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);
    Category toCategory(CategoryResponse request);
    void updateCategory(CategoryUpdate categoryUpdate, @MappingTarget Category category);
    CategoryResponse toCategoryResponse(Category category);
}
