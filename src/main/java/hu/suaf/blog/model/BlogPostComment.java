package hu.suaf.blog.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
public class BlogPostComment extends AuditableEntity<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Text can not be empty")
    private String text;

    @ManyToOne
    @JoinColumn(name="user_id")
    private BlogUser user;

    @ManyToOne
    @JoinColumn(name="post_id")
    private BlogPost post;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<BlogPostLike> likes;
}
