package hu.suaf.blog.rest.hateoas.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@NoArgsConstructor
public class BlogPostCategoryResource extends RepresentationModel<BlogPostCategoryResource> {

    private String name;
}
