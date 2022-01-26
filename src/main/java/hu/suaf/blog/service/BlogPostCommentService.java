package hu.suaf.blog.service;

import hu.suaf.blog.model.BlogPostComment;
import hu.suaf.blog.repository.BlogPostCommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogPostCommentService {

    private BlogPostCommentRepository blogPostCommentRepository;

    @Autowired
    public void setBlogPostCommentRepository(BlogPostCommentRepository blogPostCommentRepository) {
        this.blogPostCommentRepository = blogPostCommentRepository;
    }

    public BlogPostComment saveBlogPostComment(BlogPostComment comment){
        return blogPostCommentRepository.save(comment);
    }

    public List<BlogPostComment> getBlogPostComments(){
        return blogPostCommentRepository.findByDeletedFalse();
    }

    public void deleteBlogPostComment(long id){
        blogPostCommentRepository.deleteById(id);
        log.info("Blog post comment deleted with id: " + id);
    }

    public BlogPostComment getBlogPostCommentById(long id) {
        return blogPostCommentRepository.findById(id).orElse(null);
    }
}
