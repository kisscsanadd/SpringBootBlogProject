package hu.suaf.blog.service;

import hu.suaf.blog.model.BlogPostCommentLike;
import hu.suaf.blog.repository.BlogPostCommentLikeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogPostCommentLikeService {

    private BlogPostCommentLikeRepository blogPostCommentLikeRepository;

    @Autowired
    public void setBlogPostCommentLikeRepository(BlogPostCommentLikeRepository blogPostCommentLikeRepository) {
        this.blogPostCommentLikeRepository = blogPostCommentLikeRepository;
    }

    public BlogPostCommentLike saveBlogPostCommentLike(BlogPostCommentLike like){
        return blogPostCommentLikeRepository.save(like);
    }

    public List<BlogPostCommentLike> getBlogPostCommentLikes(){
        return blogPostCommentLikeRepository.findByDeletedFalse();
    }

    public void deleteBlogPostCommentLike(long id){
        blogPostCommentLikeRepository.deleteById(id);
        log.info("Blog post comment like deleted with id: " + id);
    }

    public BlogPostCommentLike getBlogPostCommentLikeById(long id) {
        return blogPostCommentLikeRepository.findById(id).orElse(null);
    }
}
