package app.store.mapper;

import app.store.dto.request.PostRequest;
import app.store.dto.response.PostResponse;
import app.store.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {

//    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "author", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
    Post toPost(PostRequest request);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "categorySlug", source = "category.slug")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "authorName", expression = "java(getAuthorFullName(post))")
    PostResponse toPostResponse(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(PostRequest request, @MappingTarget Post entity);

    default String getAuthorFullName(Post entity) {
        if (entity.getAuthor() == null) {
            return null;
        }
        return (entity.getAuthor().getFirstName() != null ? entity.getAuthor().getFirstName() : "") +
               " " +
               (entity.getAuthor().getLastName() != null ? entity.getAuthor().getLastName() : "");
    }
}
