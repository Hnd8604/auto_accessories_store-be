package app.store.service.impl;

import app.store.dto.request.PostRequest;
import app.store.dto.response.PostResponse;
import app.store.entity.Post;
import app.store.entity.PostCategory;
import app.store.entity.User;
import app.store.mapper.PostMapper;
import app.store.repository.PostCategoryRepository;
import app.store.repository.PostRepository;
import app.store.repository.UserRepository;
import app.store.service.interfaces.PostService;
import app.store.utils.SlugUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostServiceImpl implements PostService {

    PostRepository postRepository;
    PostCategoryRepository postCategoryRepository;
    UserRepository userRepository;
    SlugUtil slugUtil;
    private final PostMapper postMapper;

    @Override
    @PreAuthorize("hasAuthority('POST_CREATE')")
    public PostResponse createPost(PostRequest request, String authorName) {
        // Tìm tác giả
        User author = userRepository.findByUsername(authorName)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả với ID: " + authorName));

        // Tìm danh mục nếu có
        PostCategory category = null;
        if (request.getCategoryId() != null) {
            category = postCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + request.getCategoryId()));
        }
        Post post = postMapper.toPost(request);
        // Tạo slug từ tiêu đề
        String baseSlug = slugUtil.toSlug(request.getTitle());
        String uniqueSlug = slugUtil.createUniqueSlug(baseSlug, postRepository::existsBySlug);
        post.setSlug(uniqueSlug);
        post.setAuthor(author);
        post.setViewCount(0L);

        return postMapper.toPostResponse(postRepository.save(post));
    }

    @Override
    @PreAuthorize("hasAuthority('POST_UPDATE')")
    public PostResponse updatePost(Long id, PostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + id));

        // Cập nhật danh mục nếu có
        PostCategory category = null;
        if (request.getCategoryId() != null) {
            category = postCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + request.getCategoryId()));
        }

        // Cập nhật slug nếu tiêu đề thay đổi
        if (!post.getTitle().equals(request.getTitle())) {
            String baseSlug = slugUtil.toSlug(request.getTitle());
            String uniqueSlug = slugUtil.createUniqueSlug(baseSlug, slug ->
                    !slug.equals(post.getSlug()) && postRepository.existsBySlug(slug));
            post.setSlug(uniqueSlug);
        }

        post.setTitle(request.getTitle());
        post.setShortDescription(request.getShortDescription());
        post.setThumbnailUrl(request.getThumbnailUrl());
        post.setContent(request.getContent());
        post.setPublished(request.getPublished());
        post.setCategory(category);


        return postMapper.toPostResponse(postRepository.save(post));
    }

    @Override
    @PreAuthorize("hasAuthority('POST_DELETE')")
    public void deletePost(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + id));

        postRepository.delete(post);

    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('POST_GET_BY_ID')")
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + id));

        return postMapper.toPostResponse(post);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('POST_GET_BY_SLUG')")
    public PostResponse getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với slug: " + slug));

        return postMapper.toPostResponse(post);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('POST_GET_BY_SLUG')")
    public PostResponse getPostBySlugAndIncrementView(String slug) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với slug: " + slug));

        // Chỉ tăng view count cho bài viết đã xuất bản
        if (post.getPublished()) {
            postRepository.incrementViewCount(post.getId());
            post.setViewCount(post.getViewCount() + 1);
        }

        return postMapper.toPostResponse(post);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('POST_GET_ALL')")
    public Page<PostResponse> getAllPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        return postRepository.findAll(pageRequest).map(postMapper::toPostResponse);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('POST_GET_PUBLISHED')")
    public Page<PostResponse> getPublishedPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return postRepository.findPublishedPosts(pageRequest).map(postMapper::toPostResponse);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('POST_SEARCH')")
    public Page<PostResponse> searchPublishedPosts(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        if (keyword == null || keyword.trim().isEmpty()) {
            return postRepository.findPublishedPosts(pageRequest).map(postMapper::toPostResponse);
        }

        return postRepository.findPublishedPostsByKeyword(keyword.trim(), pageRequest)
                .map(postMapper::toPostResponse);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('POST_GET_BY_CATEGORY')")
    public Page<PostResponse> getPostsByCategory(Long categoryId, int page, int size) {
        PostCategory category = postCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + categoryId));

        PageRequest pageRequest = PageRequest.of(page, size);

        return postRepository.findPublishedPostsByCategory(category, pageRequest)
                .map(postMapper::toPostResponse);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('POST_GET_RELATED')")
    public List<PostResponse> getRelatedPosts(Long postId, int limit) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + postId));

        if (post.getCategory() == null) {
            return List.of();
        }

        PageRequest pageRequest = PageRequest.of(0, limit);

        return postRepository.findRelatedPosts(post.getCategory(), postId, pageRequest)
                .stream()
                .map(postMapper::toPostResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('POST_GET_MOST_VIEWED')")
    public List<PostResponse> getMostViewedPosts(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);

        return postRepository.findMostViewedPosts(pageRequest)
                .stream()
                .map(postMapper::toPostResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAuthority('POST_TOGGLE_PUBLISH')")
    public void togglePublishStatus(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + id));

        post.setPublished(!post.getPublished());
        postRepository.save(post);

    }
}
