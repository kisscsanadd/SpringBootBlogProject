package hu.suaf.blog.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BlogPostLike extends AuditableEntity<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private BlogUser user;

    @ManyToOne
    @JoinColumn(name="post_id")
    private BlogPost post;
}
