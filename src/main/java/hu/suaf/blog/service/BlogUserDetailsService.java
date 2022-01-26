package hu.suaf.blog.service;


import hu.suaf.blog.model.BlogUser;
import hu.suaf.blog.model.Role;
import hu.suaf.blog.repository.BlogUserRepository;
import hu.suaf.blog.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service("userDetailsService")
@Transactional
@Slf4j
public class BlogUserDetailsService implements UserDetailsService {

    private BlogUserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public BlogUserDetailsService(BlogUserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public BlogUser registerUser(BlogUser blogUser){
        Role role = roleRepository.findByName("USER");
        blogUser.setRoles(Arrays.asList(role));

        return userRepository.save(blogUser);
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        BlogUser user = userRepository.findByUsername(usernameOrEmail);

        if(user == null){
            user = userRepository.findByEmail(usernameOrEmail);

            if(user == null){
                throw new UsernameNotFoundException("Could not find user with username (or email): " + usernameOrEmail);
            }
        }

        log.info(user.getUsername() + " found");
        return user;
    }


}
