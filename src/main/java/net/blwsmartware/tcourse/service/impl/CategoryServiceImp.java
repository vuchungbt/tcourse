package net.blwsmartware.tcourse.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.blwsmartware.tcourse.dto.request.post.CategoryRequest;
import net.blwsmartware.tcourse.dto.request.post.CategoryUpdate;
import net.blwsmartware.tcourse.dto.response.DataResponse;
import net.blwsmartware.tcourse.dto.response.post.CategoryResponse;
import net.blwsmartware.tcourse.entity.Category;
import net.blwsmartware.tcourse.enums.ErrorResponse;
import net.blwsmartware.tcourse.exception.AppRuntimeException;
import net.blwsmartware.tcourse.mapper.CategoryMapper;
import net.blwsmartware.tcourse.repository.CategoryRepository;
import net.blwsmartware.tcourse.service.CategoryService;
import net.blwsmartware.tcourse.util.DataResponseUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImp implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = categoryMapper.toCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public DataResponse<CategoryResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<Category> pageOfCategory = categoryRepository.findAll(pageable);
        List<Category> userList = pageOfCategory.getContent();
        List<CategoryResponse> categoryResponses = userList.stream().map(categoryMapper::toCategoryResponse).toList();
        return DataResponseUtils.convertPageInfo(pageOfCategory,categoryResponses);
    }

    @Override
    public List<CategoryResponse> getAll() {
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(categoryMapper::toCategoryResponse).toList();
    }

    @Override
    public CategoryResponse getCategoryByID(long id) {
        return categoryMapper.toCategoryResponse(categoryRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.CATEGORY_NOT_FOUND)));
    }

    @Override
    public CategoryResponse updateCategory(long id, CategoryUpdate update) {

        Category old = categoryRepository.findById(id)
                .orElseThrow(() -> new AppRuntimeException(ErrorResponse.CATEGORY_NOT_FOUND));
        categoryMapper.updateCategory(update,old);
        return categoryMapper.toCategoryResponse(categoryRepository.save(old));
    }

    @Override
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }
}
