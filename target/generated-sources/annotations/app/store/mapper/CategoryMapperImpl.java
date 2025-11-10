package app.store.mapper;

import app.store.dto.request.CategoryRequest;
import app.store.dto.response.CategoryResponse;
import app.store.dto.response.ProductResponse;
import app.store.entity.Category;
import app.store.entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-10T14:29:41+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Category toCategory(CategoryRequest request) {
        if ( request == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.name( request.getName() );
        category.description( request.getDescription() );

        return category.build();
    }

    @Override
    public CategoryResponse toCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse.CategoryResponseBuilder categoryResponse = CategoryResponse.builder();

        categoryResponse.products( productListToProductResponseList( category.getProducts() ) );
        if ( category.getId() != null ) {
            categoryResponse.id( String.valueOf( category.getId() ) );
        }
        categoryResponse.name( category.getName() );
        categoryResponse.description( category.getDescription() );

        return categoryResponse.build();
    }

    @Override
    public void updateCategory(Category category, CategoryRequest request) {
        if ( request == null ) {
            return;
        }

        category.setName( request.getName() );
        category.setDescription( request.getDescription() );
    }

    protected List<ProductResponse> productListToProductResponseList(List<Product> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductResponse> list1 = new ArrayList<ProductResponse>( list.size() );
        for ( Product product : list ) {
            list1.add( productMapper.toProductResponse( product ) );
        }

        return list1;
    }
}
