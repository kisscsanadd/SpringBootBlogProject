package hu.suaf.blog.controller;


import hu.suaf.blog.model.BlogPost;
import hu.suaf.blog.model.BlogPostCategory;
import hu.suaf.blog.model.BlogPostComment;
import hu.suaf.blog.model.BlogUser;
import hu.suaf.blog.repository.BlogPostCategoryRepository;
import hu.suaf.blog.service.BlogPostCommentService;
import hu.suaf.blog.service.BlogPostService;
import hu.suaf.blog.service.BlogUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    private BlogPostCommentService blogPostCommentService;

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private BlogPostCategoryRepository blogPostCategoryRepository;

    @GetMapping
    public String listOfBlogPostComments(Model model){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("comments", blogPostCommentService.getBlogPostComments());

        return "home";
    }

    @PostMapping
    public String listOfBlogPostCommentsPost(Model model){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);

        model.addAttribute("blogposts", blogPostCommentService.getBlogPostComments());
        model.addAttribute("currentUser", currentUser);
        return "home";
    }

    @GetMapping("/create/{postid}")
    public String showBlogPostCommentCreateForm(@PathVariable long postid, BlogPostComment comment, Model model){
        model.addAttribute("comment", comment);
        BlogPost blogpost = blogPostService.getBlogPostById(postid);
        model.addAttribute("blogpost", blogpost);
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        return "comment-create";
    }

    @GetMapping("/edit/{postid}/{id}")
    public String editBlogPostCommentForm(@PathVariable long postid, @PathVariable long id, Model model){

        List<BlogPostCategory> categories = blogPostCategoryRepository.findByDeletedFalse();
        model.addAttribute("categories", categories);
        BlogPost blogpost = blogPostService.getBlogPostById(postid);
        model.addAttribute("blogpost", blogpost);
        BlogPostComment comment = blogPostCommentService.getBlogPostCommentById(id);

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("comment", comment);
        return "comment-create";
    }

    @PostMapping("/edit/{postid}/{id}")
    public String editBlogPostComment(@Valid BlogPostComment comment,@PathVariable long postid, BindingResult result, Model model){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogPost currentPost = blogPostService.getBlogPostById(postid);
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        if (result.hasErrors()) {
            return "comment-create";
        }
        comment.setUser(currentUser);
        comment.setPost(currentPost);
        blogPostCommentService.saveBlogPostComment(comment);

        return "redirect:/";
    }

    @PostMapping("/create/{postid}")
    public String createBlogPostComment(@Valid BlogPostComment comment,@PathVariable long postid, BindingResult result, Model model){
        BlogPost currentPost = blogPostService.getBlogPostById(postid);
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("comment", comment);
        if (result.hasErrors()) {
            return "comment-create";
        }
        comment.setUser(currentUser);
        comment.setPost(currentPost);

        blogPostCommentService.saveBlogPostComment(comment);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteBlogPostComment(@PathVariable long id){
        BlogPostComment comment = blogPostCommentService.getBlogPostCommentById(id);
        comment.setDeleted(true);
        blogPostCommentService.saveBlogPostComment(comment);
        return "redirect:/";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @Autowired
    public void setBlogPostCommentService(BlogPostCommentService blogPostCommentService) {
        this.blogPostCommentService = blogPostCommentService;
    }
}
