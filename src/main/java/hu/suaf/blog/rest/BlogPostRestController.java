package hu.suaf.blog.rest;

import hu.suaf.blog.model.BlogPost;
import hu.suaf.blog.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogPostRestController {

    @Autowired
    private BlogPostService blogPostService;

    @GetMapping(value = "/blogposts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BlogPost> blogPosts(){
        return blogPostService.getBlogPosts();
    }

    @GetMapping(value = "/blogposts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<BlogPost> blogPost(@PathVariable Long id){
        BlogPost blogPost = blogPostService.getBlogPostById(id);
        if(blogPost == null){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(blogPost, HttpStatus.OK);
    }

    @PostMapping(value = "/blogposts", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<BlogPost> createBlogPost(@Valid @RequestBody BlogPost blogPost){
        try{
            BlogPost result = blogPostService.saveBlogPost(blogPost);
            ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().build();
    }


}
