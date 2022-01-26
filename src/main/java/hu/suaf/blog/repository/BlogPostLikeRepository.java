package hu.suaf.blog.repository;

import hu.suaf.blog.model.BlogPostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlogPostLikeRepository extends JpaRepository<BlogPostLike, Long> {

    public List<BlogPostLike> findByDeletedFalse();
}
