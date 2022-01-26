package hu.suaf.blog.rest.hateoas.assembler;

import hu.suaf.blog.model.BlogPostCategory;
import hu.suaf.blog.rest.hateoas.BlogPostCategoryRestHateoasController;
import hu.suaf.blog.rest.hateoas.BlogPostRestHateoasController;
import hu.suaf.blog.rest.hateoas.resources.BlogPostCategoryResource;
import hu.suaf.blog.rest.hateoas.resources.BlogPostResource;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class BlogPostCategoryResourceAssembler extends RepresentationModelAssemblerSupport<BlogPostCategory, BlogPostCategoryResource> {


    public BlogPostCategoryResourceAssembler() {
        super(BlogPostCategoryRestHateoasController.class, BlogPostCategoryResource.class);
    }

    @Override
    public BlogPostCategoryResource toModel(BlogPostCategory entity) {
        return createModelWithId(entity.getId(), entity);
    }
}
