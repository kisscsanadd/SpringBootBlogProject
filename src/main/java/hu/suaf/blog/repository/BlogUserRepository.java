package hu.suaf.blog.repository;

import hu.suaf.blog.model.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {
    BlogUser findByEmail(String email);

    BlogUser findByUsername(String username);

    public List<BlogUser> findByDeletedFalse();

}
