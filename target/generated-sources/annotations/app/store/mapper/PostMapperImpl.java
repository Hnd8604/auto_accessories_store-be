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
    date = "2025-12-03T09:19:42+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post toPost(PostRequest request) {
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
    public PostResponse toPostResponse(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponse postResponse = new PostResponse();

        postResponse.setCategoryName( postCategoryName( post ) );
        postResponse.setAuthorId( postAuthorId( post ) );
        postResponse.setId( post.getId() );
        postResponse.setTitle( post.getTitle() );
        postResponse.setSlug( post.getSlug() );
        postResponse.setShortDescription( post.getShortDescription() );
        postResponse.setThumbnailUrl( post.getThumbnailUrl() );
        postResponse.setContent( post.getContent() );
        postResponse.setPublished( post.getPublished() );
        postResponse.setViewCount( post.getViewCount() );
        postResponse.setCreatedAt( post.getCreatedAt() );
        postResponse.setUpdatedAt( post.getUpdatedAt() );

        postResponse.setAuthorName( getAuthorFullName(post) );

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

    private String postCategoryName(Post post) {
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

    private String postAuthorId(Post post) {
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
