package hu.suaf.blog.repository;

import hu.suaf.blog.model.BlogPost;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    public List<BlogPost> findByDeletedFalse();

    public List<BlogPost> findByDeletedFalse(Sort var1);
}
