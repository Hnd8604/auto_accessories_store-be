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
import app.store.service.PostService;
import app.store.utils.SlugUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    PostMapper postMapper;
    @Override
    public PostResponse createPost(PostRequest request, String authorName) {
        // Tìm tác giả
        User author = userRepository.findByUsername(authorName)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả với Name: " + authorName));
        
        // Tìm danh mục nếu có
        PostCategory category = null;
        if (request.getCategoryId() != null) {
            category = postCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + request.getCategoryId()));
        }
        
        // Tạo slug từ tiêu đề
        String baseSlug = slugUtil.toSlug(request.getTitle());
        String uniqueSlug = slugUtil.createUniqueSlug(baseSlug, postRepository::existsBySlug);
        
        Post post = postMapper.toPost(request);
        post.setAuthor(author);
        post.setCategory(category);
        post.setSlug(uniqueSlug);
        post.setViewCount(0L);
        

        return postMapper.toPostResponse(postRepository.save(post));
    }
    
    @Override
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
    public void deletePost(Long id) {
        log.info("Deleting post with ID: {}", id);
        
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + id));
        
        postRepository.delete(post);
        log.info("Deleted post with ID: {}", id);
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
    public Page<PostResponse> getAllPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, 
            Sort.by(Sort.Direction.DESC, "createdAt"));
        
        return postRepository.findAll(pageRequest).map(postMapper::toPostResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> getPublishedPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        
        return postRepository.findPublishedPosts(pageRequest).map(postMapper::toPostResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
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
    public Page<PostResponse> getPostsByCategory(Long categoryId, int page, int size) {
        PostCategory category = postCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + categoryId));
        
        PageRequest pageRequest = PageRequest.of(page, size);
        
        return postRepository.findPublishedPostsByCategory(category, pageRequest)
                .map(postMapper::toPostResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
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
    public List<PostResponse> getMostViewedPosts(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        
        return postRepository.findMostViewedPosts(pageRequest)
                .stream()
                .map(postMapper::toPostResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public void togglePublishStatus(Long id) {
        log.info("Toggling publish status for post with ID: {}", id);
        
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + id));
        
        post.setPublished(!post.getPublished());
        postRepository.save(post);
        
        log.info("Toggled publish status for post with ID: {} to: {}", id, post.getPublished());
    }
    
//    private PostResponse mapToResponse(Post post) {
//        PostResponse response = new PostResponse();
//        response.setId(post.getId());
//        response.setTitle(post.getTitle());
//        response.setSlug(post.getSlug());
//        response.setShortDescription(post.getShortDescription());
//        response.setThumbnailUrl(post.getThumbnailUrl());
//        response.setContent(post.getContent());
//        response.setPublished(post.getPublished());
//        response.setViewCount(post.getViewCount());
//        response.setCreatedAt(post.getCreatedAt());
//        response.setUpdatedAt(post.getUpdatedAt());
//
//        // Thông tin danh mục
//        if (post.getCategory() != null) {
//            response.setCategoryId(post.getCategory().getId());
//            response.setCategoryName(post.getCategory().getName());
//            response.setCategorySlug(post.getCategory().getSlug());
//        }
//
//        // Thông tin tác giả
//        if (post.getAuthor() != null) {
//            response.setAuthorId(post.getAuthor().getId());
//            response.setAuthorName(post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName());
//        }
//
//        return response;
//    }
}
