package hu.suaf.blog.service;


import hu.suaf.blog.model.BlogUser;
import hu.suaf.blog.repository.BlogUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogUserService {

    private BlogUserRepository blogUserRepository;

    @Autowired
    public void setBlogUserRepository(BlogUserRepository blogUserRepository) {
        this.blogUserRepository = blogUserRepository;
    }

    public BlogUser saveBlogUser(BlogUser user){
        return blogUserRepository.save(user);
    }

    public List<BlogUser> getBlogUsers(){
        return blogUserRepository.findByDeletedFalse();
    }

    public void deleteBlogUser(long id){
        blogUserRepository.deleteById(id);
        log.info("Blog user deleted with id: " + id);
    }

    public BlogUser getBlogUserById(long id) {
        return blogUserRepository.findById(id).orElse(null);
    }

    public BlogUser getBlogUserByUsername(String username) {
        return blogUserRepository.findByUsername(username);
    }
}
