package hu.suaf.blog.service;

import hu.suaf.blog.model.BlogPostLike;
import hu.suaf.blog.repository.BlogPostLikeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogPostLikeService {

    private BlogPostLikeRepository blogPostLikeRepository;

    @Autowired
    public void setBlogPostLikeRepository(BlogPostLikeRepository blogPostLikeRepository) {
        this.blogPostLikeRepository = blogPostLikeRepository;
    }

    public BlogPostLike saveBlogPostLike(BlogPostLike like){
        return blogPostLikeRepository.save(like);
    }

    public List<BlogPostLike> getBlogPostLikes(){
        return blogPostLikeRepository.findByDeletedFalse();
    }

    public void deleteBlogPostLike(long id){
        blogPostLikeRepository.deleteById(id);
        log.info("Blog post like deleted with id: " + id);
    }

    public BlogPostLike getBlogPostLikeById(long id) {
        return blogPostLikeRepository.findById(id).orElse(null);
    }
}
