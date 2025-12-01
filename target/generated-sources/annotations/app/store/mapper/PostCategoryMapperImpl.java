package app.store.mapper;

import app.store.dto.request.PostCategoryRequest;
import app.store.dto.response.PostCategoryResponse;
import app.store.entity.PostCategory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-01T18:10:40+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class PostCategoryMapperImpl implements PostCategoryMapper {

    @Override
    public PostCategory toPostCategory(PostCategoryRequest request) {
        if ( request == null ) {
            return null;
        }

        PostCategory.PostCategoryBuilder postCategory = PostCategory.builder();

        postCategory.name( request.getName() );
        postCategory.description( request.getDescription() );

        return postCategory.build();
    }

    @Override
    public PostCategoryResponse toPostCategoryResponse(PostCategory postCategory) {
        if ( postCategory == null ) {
            return null;
        }

        PostCategoryResponse postCategoryResponse = new PostCategoryResponse();

        postCategoryResponse.setId( postCategory.getId() );
        postCategoryResponse.setName( postCategory.getName() );
        postCategoryResponse.setSlug( postCategory.getSlug() );
        postCategoryResponse.setDescription( postCategory.getDescription() );
        postCategoryResponse.setCreatedAt( postCategory.getCreatedAt() );
        postCategoryResponse.setUpdatedAt( postCategory.getUpdatedAt() );

        postCategoryResponse.setPostCount( postCategory.getPosts() != null ? (long) postCategory.getPosts().size() : 0L );

        return postCategoryResponse;
    }

    @Override
    public void updateEntityFromRequest(PostCategoryRequest request, PostCategory postCategory) {
        if ( request == null ) {
            return;
        }

        postCategory.setName( request.getName() );
        postCategory.setDescription( request.getDescription() );
    }
}
