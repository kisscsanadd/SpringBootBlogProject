package hu.suaf.blog.rest;

import hu.suaf.blog.model.BlogPost;
import hu.suaf.blog.model.BlogPostCategory;
import hu.suaf.blog.service.BlogPostCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogPostCategoryRestController {

    @Autowired
    private BlogPostCategoryService blogPostCategoryService;

    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BlogPostCategory> blogPostsCategory(){
        return blogPostCategoryService.getBlogPostCategories();
    }

    @GetMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<BlogPostCategory> blogPostCategory(@PathVariable Long id){
        BlogPostCategory blogPostCategory = blogPostCategoryService.getBlogPostCategoryById(id);
        if(blogPostCategory == null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(blogPostCategory, HttpStatus.OK);
    }

    @PostMapping(value = "/categories", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<BlogPost> createBlogPostCategory(@Valid @RequestBody BlogPostCategory blogPostCategory){
        try{
            BlogPostCategory result = blogPostCategoryService.saveBlogPostCategory(blogPostCategory);
            ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
