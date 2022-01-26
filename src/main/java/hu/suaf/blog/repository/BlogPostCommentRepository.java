package hu.suaf.blog.repository;

import hu.suaf.blog.model.BlogPostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlogPostCommentRepository extends JpaRepository<BlogPostComment, Long> {

    public List<BlogPostComment> findByDeletedFalse();
}
