package app.store.mapper;

import app.store.dto.request.CategoryRequest;
import app.store.dto.response.CategoryResponse;
import app.store.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);
    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "id", ignore = true) // Ignore the ID field during update
    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}
