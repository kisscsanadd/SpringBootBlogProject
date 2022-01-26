package hu.suaf.blog.repository;

import hu.suaf.blog.model.BlogPost;
import hu.suaf.blog.model.BlogPostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlogPostCategoryRepository extends JpaRepository<BlogPostCategory, Long> {

    public List<BlogPostCategory> findByDeletedFalse();
}
