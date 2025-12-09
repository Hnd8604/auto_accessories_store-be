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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

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
    public PostResponse createPost(PostRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authorName = authentication.getName(); // Lấy ID từ JWT token
        // Tìm tác giả
        User author = userRepository.findByUsername(authorName)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả với ID: " + authorName));

        // Tìm danh mục nếu có
        PostCategory postCategory = null;
        if (request.getCategoryId() != null) {
            postCategory = postCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + request.getCategoryId()));
        }

        Post post = postMapper.toPost(request);
        // Tạo slug từ tiêu đề
        String baseSlug = slugUtil.toSlug(request.getTitle());
        String uniqueSlug = slugUtil.createUniqueSlug(baseSlug, postRepository::existsBySlug);
        post.setSlug(uniqueSlug);
        post.setAuthor(author);
        post.setViewCount(0L);
        post.setCategory(postCategory);

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
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + id));

        return postMapper.toPostResponse(post);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với slug: " + slug));

        return postMapper.toPostResponse(post);
    }

    @Override
    @Transactional
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
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(postMapper::toPostResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> getPublishedPosts(Pageable pageable) {

        return postRepository.findPublishedPosts(pageable).map(postMapper::toPostResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> searchPublishedPosts(String keyword, Pageable pageable) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return postRepository.findPublishedPosts(pageable).map(postMapper::toPostResponse);
        }

        return postRepository.findPublishedPostsByKeyword(keyword.trim(), pageable)
                .map(postMapper::toPostResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> getPostsByCategory(Long categoryId, Pageable pageable) {
        PostCategory category = postCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + categoryId));

        return postRepository.findPublishedPostsByCategory(category, pageable)
                .map(postMapper::toPostResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> getRelatedPosts(Long postId, Pageable pageable) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + postId));

        if (post.getCategory() == null) {
            return Page.empty();
        }

        return postRepository.findRelatedPosts(post.getCategory(), postId,pageable)
                .map(postMapper::toPostResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> getMostViewedPosts(Pageable pageable) {
        return postRepository.findMostViewedPosts(pageable)
                .map(postMapper::toPostResponse);
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
