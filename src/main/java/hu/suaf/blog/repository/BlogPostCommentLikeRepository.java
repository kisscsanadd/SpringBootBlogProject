package hu.suaf.blog.repository;

import hu.suaf.blog.model.BlogPostCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlogPostCommentLikeRepository extends JpaRepository<BlogPostCommentLike, Long> {

    public List<BlogPostCommentLike> findByDeletedFalse();
}
