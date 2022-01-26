package hu.suaf.blog.rest.hateoas;

import hu.suaf.blog.model.BlogPost;
import hu.suaf.blog.rest.hateoas.assembler.BlogPostResourceAssembler;
import hu.suaf.blog.rest.hateoas.resources.BlogPostResource;
import hu.suaf.blog.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequestMapping("/api-v2")
public class BlogPostRestHateoasController {

    @Autowired
    private BlogPostService blogPostService;

    @GetMapping("/blogposts")
    public HttpEntity<CollectionModel<EntityModel<BlogPost>>> getBlogPosts(){
        List<BlogPost> blogPosts = blogPostService.getBlogPosts();
        CollectionModel<EntityModel<BlogPost>> blogPostsResource = CollectionModel.wrap(blogPosts);

        blogPostsResource.add(linkTo(methodOn(BlogPostRestHateoasController.class).getBlogPosts()).withSelfRel());

        return ResponseEntity.ok(blogPostsResource);
    }

    @GetMapping("/blogposts/{id}")
    public HttpEntity<EntityModel<BlogPostResource>> getBlogPost(@PathVariable Long id){
        BlogPost blogPost = blogPostService.getBlogPostById(id);
        if(blogPost == null){
            return ResponseEntity.notFound().build();
        }

        EntityModel<BlogPostResource> blogPostResource = EntityModel.of(new BlogPostResourceAssembler().toModel(blogPost));

        return ResponseEntity.ok(blogPostResource);
    }

    @PutMapping("/blogposts/{id}")
    public HttpEntity<EntityModel<BlogPost>> updateBlogPost(@PathVariable Long id, @RequestBody BlogPost blogPost) {

        return ResponseEntity.notFound().build();
    }
}
