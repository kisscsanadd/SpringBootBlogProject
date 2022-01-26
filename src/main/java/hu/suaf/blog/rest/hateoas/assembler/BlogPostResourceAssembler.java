package hu.suaf.blog.rest.hateoas.assembler;

import hu.suaf.blog.model.BlogPost;
import hu.suaf.blog.rest.hateoas.BlogPostRestHateoasController;
import hu.suaf.blog.rest.hateoas.resources.BlogPostResource;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class BlogPostResourceAssembler extends RepresentationModelAssemblerSupport<BlogPost, BlogPostResource> {


    public BlogPostResourceAssembler() {
        super(BlogPostRestHateoasController.class, BlogPostResource.class);
    }

    @Override
    public BlogPostResource toModel(BlogPost entity) {
        return createModelWithId(entity.getId(), entity);
    }
}
