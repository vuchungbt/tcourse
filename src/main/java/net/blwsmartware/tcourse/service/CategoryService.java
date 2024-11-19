package net.blwsmartware.tcourse.service;

import net.blwsmartware.tcourse.dto.request.post.CategoryRequest;
import net.blwsmartware.tcourse.dto.request.post.CategoryUpdate;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.post.CategoryResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    DataResponse<CategoryResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy);
    CategoryResponse getCategoryByID(long id);
    CategoryResponse updateCategory(long id, CategoryUpdate update);
    void delete(long id);
}
