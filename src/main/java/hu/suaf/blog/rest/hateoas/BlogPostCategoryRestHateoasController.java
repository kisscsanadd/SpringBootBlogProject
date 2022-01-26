package hu.suaf.blog.rest.hateoas;

import hu.suaf.blog.model.BlogPostCategory;
import hu.suaf.blog.rest.hateoas.assembler.BlogPostCategoryResourceAssembler;
import hu.suaf.blog.rest.hateoas.resources.BlogPostCategoryResource;
import hu.suaf.blog.service.BlogPostCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequestMapping("/api-v2")
public class BlogPostCategoryRestHateoasController {

    @Autowired
    private BlogPostCategoryService blogPostCategoryService;

    @GetMapping("/categories")
    public HttpEntity<CollectionModel<EntityModel<BlogPostCategory>>> getBlogPostCategories(){
        List<BlogPostCategory> blogPostCategories = blogPostCategoryService.getBlogPostCategories();
        CollectionModel<EntityModel<BlogPostCategory>> blogPostsResource = CollectionModel.wrap(blogPostCategories);

        blogPostsResource.add(linkTo(methodOn(BlogPostCategoryRestHateoasController.class).getBlogPostCategories()).withSelfRel());

        return ResponseEntity.ok(blogPostsResource);
    }

    @GetMapping("/categories/{id}")
    public HttpEntity<EntityModel<BlogPostCategoryResource>> getBlogPostCategory(@PathVariable Long id){
        BlogPostCategory blogPostCategory = blogPostCategoryService.getBlogPostCategoryById(id);
        if(blogPostCategory == null){
            return ResponseEntity.notFound().build();
        }

        EntityModel<BlogPostCategoryResource> blogPostCategoryResource = EntityModel.of(new BlogPostCategoryResourceAssembler().toModel(blogPostCategory));

        return ResponseEntity.ok(blogPostCategoryResource);
    }

    @PutMapping("/categories/{id}")
    public HttpEntity<EntityModel<BlogPostCategory>> updateBlogPostCategory(@PathVariable Long id, @RequestBody BlogPostCategory blogPostCategory) {

        return ResponseEntity.notFound().build();
    }
}
