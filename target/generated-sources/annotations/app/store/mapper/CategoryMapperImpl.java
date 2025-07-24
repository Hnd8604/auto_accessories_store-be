package app.store.mapper;

import app.store.dto.request.CategoryRequest;
import app.store.dto.response.CategoryResponse;
import app.store.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-24T21:54:17+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toCategory(CategoryRequest request) {
        if ( request == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.categoryName( request.getCategoryName() );
        category.description( request.getDescription() );

        return category.build();
    }

    @Override
    public CategoryResponse toCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse.CategoryResponseBuilder categoryResponse = CategoryResponse.builder();

        if ( category.getCategoryId() != null ) {
            categoryResponse.categoryId( String.valueOf( category.getCategoryId() ) );
        }
        categoryResponse.categoryName( category.getCategoryName() );
        categoryResponse.description( category.getDescription() );

        return categoryResponse.build();
    }

    @Override
    public void updateCategory(Category category, CategoryRequest request) {
        if ( request == null ) {
            return;
        }

        category.setCategoryName( request.getCategoryName() );
        category.setDescription( request.getDescription() );
    }
}
