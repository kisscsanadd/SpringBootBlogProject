package hu.suaf.blog.service;

import hu.suaf.blog.model.BlogPostCategory;
import hu.suaf.blog.repository.BlogPostCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogPostCategoryService {

    private BlogPostCategoryRepository blogPostCategoryRepository;

    @Autowired
    public void setBlogPostCategoryRepository(BlogPostCategoryRepository blogPostCategoryRepository) {
        this.blogPostCategoryRepository = blogPostCategoryRepository;
    }

    public BlogPostCategory saveBlogPostCategory(BlogPostCategory category){
        return blogPostCategoryRepository.save(category);
    }

    public List<BlogPostCategory> getBlogPostCategories(){
        return (List<BlogPostCategory>) blogPostCategoryRepository.findByDeletedFalse();

    }

    public void deleteBlogPostCategory(long id){
        blogPostCategoryRepository.deleteById(id);
        log.info("Blog post category deleted with id: " + id);
    }

    public BlogPostCategory getBlogPostCategoryById(long id) {
        return blogPostCategoryRepository.findById(id).orElse(null);
    }
}
