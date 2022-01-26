package hu.suaf.blog.config;

import hu.suaf.blog.model.BlogUser;
import hu.suaf.blog.model.Role;
import hu.suaf.blog.repository.BlogPostCategoryRepository;
import hu.suaf.blog.repository.BlogPostRepository;
import hu.suaf.blog.repository.RoleRepository;
import hu.suaf.blog.service.BlogPostCategoryService;
import hu.suaf.blog.service.BlogUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
@Slf4j
public class UserDataSetup implements ApplicationListener<ContextRefreshedEvent> {

    private RoleRepository roleRepository;
    private BlogPostRepository blogPostRepository;
    private BlogPostCategoryRepository blogPostCategoryRepository;

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private BlogPostCategoryService blogPostCategoryService;

    private PasswordEncoder passwordEncoder;

    @Autowired

    public UserDataSetup(RoleRepository roleRepository,
                         BlogPostRepository blogPostRepository) {
        this.roleRepository = roleRepository;
        this.blogPostRepository = blogPostRepository;

    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    boolean alreadySetup = false;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup){
            return;
        }

        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("USER");

        if(blogUserService.getBlogUsers().isEmpty()){
            //admin
            Role adminRole = roleRepository.findByName("ADMIN");
            BlogUser user = new BlogUser();
            user.setEnabled(true);
            user.setDeleted(false);
            user.setRoles(Arrays.asList(adminRole));
            user.setPassword(passwordEncoder.encode("admin"));
            user.setUsername("admin");
            user.setEmail("admin@test.com");

            blogUserService.saveBlogUser(user);


            //user
            Role userRole = roleRepository.findByName("USER");
            BlogUser user2 = new BlogUser();
            user2.setEnabled(true);
            user2.setDeleted(false);
            user2.setRoles(Arrays.asList(userRole));
            user2.setPassword(passwordEncoder.encode("user"));
            user2.setUsername("user");
            user2.setEmail("user@test.com");


            blogUserService.saveBlogUser(user2);

            alreadySetup = true;
            log.info("test user saved");
        }
    }

    @Transactional
    Role createRoleIfNotExists(String name) {
        Role role = roleRepository.findByName(name);

        if(role == null){
            role = new Role(name);
            roleRepository.save(role);
        }

        return role;
    }
}
