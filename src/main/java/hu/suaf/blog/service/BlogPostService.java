package hu.suaf.blog.service;

import hu.suaf.blog.model.BlogPost;
import hu.suaf.blog.repository.BlogPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogPostService {

    private BlogPostRepository blogPostRepository;

    @Autowired
    public void setBlogPostRepository(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public BlogPost saveBlogPost(BlogPost post){
        return blogPostRepository.save(post);
    }

    public List<BlogPost> getBlogPosts(){
        return blogPostRepository.findByDeletedFalse();
    }

    public List<BlogPost> getBlogPostsDateAscending(){
        return blogPostRepository.findByDeletedFalse(Sort.by("createdAt").ascending());

    }

    public List<BlogPost> getBlogPostsDateDescending(){
        return blogPostRepository.findByDeletedFalse(Sort.by("createdAt").descending());
    }

    public void deleteBlogPost(long id){
        blogPostRepository.deleteById(id);
        log.info("Blog post deleted with id: " + id);
    }

    public BlogPost getBlogPostById(long id) {
        return blogPostRepository.findById(id).orElse(null);
    }
}
