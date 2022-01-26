package hu.suaf.blog.controller;



import hu.suaf.blog.model.BlogPost;
import hu.suaf.blog.model.BlogUser;
import hu.suaf.blog.service.BlogUserDetailsService;
import hu.suaf.blog.service.BlogUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {

    private BlogUserService blogUserService;

    @Autowired
    private BlogUserDetailsService blogUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String listOfBlogUsers(Model model){
        model.addAttribute("users", blogUserService.getBlogUsers());

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);

        return "users";
    }

    @PostMapping
    public String listOfBlogUsersPost(Model model){
        model.addAttribute("users", blogUserService.getBlogUsers());
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        return "users";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable long id, Model model){
        BlogUser user = blogUserService.getBlogUserById(id);
        model.addAttribute("currentUser", user);
        return "profile";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@Valid BlogUser user, BindingResult result){
        if(result.hasErrors()){
            return "profile";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        blogUserDetailsService.registerUser(user);
        return "redirect:/";
    }
/*
    @GetMapping("/create")
    public String showBlogPostCreateForm(BlogPost blogpost, Model model){
        List<BlogPostCategory> categories = blogPostCategoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("blogpost", blogpost);
        return "blogpost-create";
    }

    @GetMapping("/edit/{id}")
    public String editBlogPostForm(@PathVariable long id, Model model){
        BlogPost post = blogUserService.getBlogPostById(id);
        model.addAttribute("blogpost", post);
        return "blogpost-create";
    }

    @PostMapping("/edit/{id}")
    public String editBlogPost(@Valid BlogPost post, BindingResult result){
        if (result.hasErrors()) {
            return "blogpost-create";
        }

        blogUserService.saveBlogPost(post);
        return "redirect:/blogpost";
    }*/
/*
    @PostMapping("/create")
    public String createBlogPost(@Valid BlogPost post, BindingResult result){
        if (result.hasErrors()) {
            return "blogpost-create";
        }

        blogUserService.saveBlogPost(post);
        return "redirect:/users";
    }*/

    @GetMapping("/delete/{id}")
    public String deleteBlogUser(@PathVariable long id){
        blogUserService.deleteBlogUser(id);
        return "redirect:/users";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @Autowired
    public void setBlogUserService(BlogUserService blogUserService) {
        this.blogUserService = blogUserService;
    }
}
