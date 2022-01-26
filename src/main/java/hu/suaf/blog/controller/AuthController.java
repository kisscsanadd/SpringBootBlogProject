package hu.suaf.blog.controller;


import hu.suaf.blog.model.BlogUser;
import hu.suaf.blog.service.BlogUserDetailsService;
import hu.suaf.blog.service.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private BlogUserDetailsService blogUserDetailsService;

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm(Model model, BlogUser blogUser){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("blogUser", blogUser);
        return "login";
    }

    @PostMapping("/login")
    public String loginFormPost(Model model, BlogUser user){
        model.addAttribute("currentUser", user);
        return "home";
    }

    @PostMapping("/login-error")
    public String loginError(Model model, BlogUser blogUser){
        model.addAttribute("loginError", true);
        model.addAttribute("blogUser", blogUser);
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(BlogUser blogUser){
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid BlogUser blogUser, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "register";
        }
        blogUser.setPassword(passwordEncoder.encode(blogUser.getPassword()));
        blogUserDetailsService.registerUser(blogUser);
        return "redirect:/";
    }

}
