package app.store.mapper;

import app.store.dto.request.PostRequest;
import app.store.dto.response.PostResponse;
import app.store.entity.Post;
import app.store.entity.PostCategory;
import app.store.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T22:56:43+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post toEntity(PostRequest request) {
        if ( request == null ) {
            return null;
        }

        Post.PostBuilder post = Post.builder();

        post.title( request.getTitle() );
        post.shortDescription( request.getShortDescription() );
        post.thumbnailUrl( request.getThumbnailUrl() );
        post.content( request.getContent() );
        post.published( request.getPublished() );

        return post.build();
    }

    @Override
    public PostResponse toResponse(Post entity) {
        if ( entity == null ) {
            return null;
        }

        PostResponse postResponse = new PostResponse();

        postResponse.setCategoryId( entityCategoryId( entity ) );
        postResponse.setCategoryName( entityCategoryName( entity ) );
        postResponse.setCategorySlug( entityCategorySlug( entity ) );
        postResponse.setAuthorId( entityAuthorId( entity ) );
        postResponse.setId( entity.getId() );
        postResponse.setTitle( entity.getTitle() );
        postResponse.setSlug( entity.getSlug() );
        postResponse.setShortDescription( entity.getShortDescription() );
        postResponse.setThumbnailUrl( entity.getThumbnailUrl() );
        postResponse.setContent( entity.getContent() );
        postResponse.setPublished( entity.getPublished() );
        postResponse.setViewCount( entity.getViewCount() );
        postResponse.setCreatedAt( entity.getCreatedAt() );
        postResponse.setUpdatedAt( entity.getUpdatedAt() );

        postResponse.setAuthorName( getAuthorFullName(entity) );

        return postResponse;
    }

    @Override
    public void updateEntityFromRequest(PostRequest request, Post entity) {
        if ( request == null ) {
            return;
        }

        entity.setTitle( request.getTitle() );
        entity.setShortDescription( request.getShortDescription() );
        entity.setThumbnailUrl( request.getThumbnailUrl() );
        entity.setContent( request.getContent() );
        entity.setPublished( request.getPublished() );
    }

    private Long entityCategoryId(Post post) {
        if ( post == null ) {
            return null;
        }
        PostCategory category = post.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityCategoryName(Post post) {
        if ( post == null ) {
            return null;
        }
        PostCategory category = post.getCategory();
        if ( category == null ) {
            return null;
        }
        String name = category.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String entityCategorySlug(Post post) {
        if ( post == null ) {
            return null;
        }
        PostCategory category = post.getCategory();
        if ( category == null ) {
            return null;
        }
        String slug = category.getSlug();
        if ( slug == null ) {
            return null;
        }
        return slug;
    }

    private String entityAuthorId(Post post) {
        if ( post == null ) {
            return null;
        }
        User author = post.getAuthor();
        if ( author == null ) {
            return null;
        }
        String id = author.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
