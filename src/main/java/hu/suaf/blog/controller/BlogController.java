package hu.suaf.blog.controller;


import hu.suaf.blog.model.*;
import hu.suaf.blog.repository.BlogPostCategoryRepository;
import hu.suaf.blog.service.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/blogpost")
@Slf4j
public class BlogController {

    private BlogPostService blogPostService;

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private BlogPostLikeService blogPostLikeService;

    @Autowired
    private BlogPostCommentLikeService blogPostCommentLikeService;

    @Autowired
    private BlogPostCommentService blogPostCommentService;

    @Autowired
    private BlogPostCategoryRepository blogPostCategoryRepository;

    @GetMapping
    public String listOfBlogPosts(Model model){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("blogposts", blogPostService.getBlogPosts());

        List<Long> postIdsLikedByCurrentUser = new ArrayList<>();

        for (BlogPostLike like : blogPostLikeService.getBlogPostLikes()){
            if(like.getUser().getUsername().equals(currentUserName)){
                postIdsLikedByCurrentUser.add(like.getPost().getId());
            }
        }
        model.addAttribute("likedpostids", postIdsLikedByCurrentUser);

        List<Long> postCommentIdsLikedByCurrentUser = new ArrayList<>();
        for (BlogPostCommentLike like : blogPostCommentLikeService.getBlogPostCommentLikes()){
            if(like.getUser().getUsername().equals(currentUserName)){
                if(like.getComment() != null){
                    postCommentIdsLikedByCurrentUser.add(like.getComment().getId());
                }
            }
        }

        model.addAttribute("likedpostcommentids", postCommentIdsLikedByCurrentUser);

        return "home";
    }

    @PostMapping
    public String listOfBlogPostsPost(Model model){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);

        model.addAttribute("blogposts", blogPostService.getBlogPosts());
        model.addAttribute("currentUser", currentUser);

        List<Long> postIdsLikedByCurrentUser = new ArrayList<>();

        for (BlogPostLike like : blogPostLikeService.getBlogPostLikes()){
            if(like.getUser().getUsername().equals(currentUserName)){
                if(like.getPost() != null){
                    postIdsLikedByCurrentUser.add(like.getPost().getId());
                }
            }
        }
        model.addAttribute("likedpostids", postIdsLikedByCurrentUser);

        List<Long> postCommentIdsLikedByCurrentUser = new ArrayList<>();
        for (BlogPostCommentLike like : blogPostCommentLikeService.getBlogPostCommentLikes()){
            if(like.getUser().getUsername().equals(currentUserName)){
                if(like.getComment() != null){
                    postCommentIdsLikedByCurrentUser.add(like.getComment().getId());
                }
            }
        }

        model.addAttribute("likedpostcommentids", postCommentIdsLikedByCurrentUser);

        return "home";
    }

    @GetMapping("/create")
    public String showBlogPostCreateForm(BlogPost blogpost, Model model){
        List<BlogPostCategory> categories = blogPostCategoryRepository.findByDeletedFalse();
        model.addAttribute("categories", categories);
        model.addAttribute("blogpost", blogpost);
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        return "blogpost-create";
    }

    @GetMapping("/edit/{id}")
    public String editBlogPostForm(@PathVariable long id, Model model){

        List<BlogPostCategory> categories = blogPostCategoryRepository.findByDeletedFalse();
        model.addAttribute("categories", categories);
        BlogPost post = blogPostService.getBlogPostById(id);

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("blogpost", post);
        return "blogpost-create";
    }

    @PostMapping("/edit/{id}")
    public String editBlogPost(@Valid BlogPost post, BindingResult result, Model model){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        if (result.hasErrors()) {
            return "blogpost-create";
        }
        post.setUser(currentUser);
        blogPostService.saveBlogPost(post);

        return "redirect:/blogpost";
    }

    @PostMapping("/create")
    public String createBlogPost(@Valid BlogPost post, BindingResult result, Model model){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("blogpost", post);
        if (result.hasErrors()) {
            return "blogpost-create";
        }
        post.setUser(currentUser);

        blogPostService.saveBlogPost(post);
        return "redirect:/blogpost";
    }

    @GetMapping("/delete/{id}")
    public String deleteBlogPost(@PathVariable long id){
        BlogPost blogPost = blogPostService.getBlogPostById(id);
        //delete likes and comments for the post
     /*   for(BlogPostComment blogPostComment : blogPostCommentService.getBlogPostComments()){
            if(blogPostComment.getPost().getId().equals(id)){
                blogPostCommentService.deleteBlogPostComment(blogPostComment.getId());
            }
        }
        for(BlogPostLike blogPostLike : blogPostLikeService.getBlogPostLikes()){
            if(blogPostLike.getPost().getId().equals(id)){
                blogPostLikeService.deleteBlogPostLike(blogPostLike.getId());
            }
        }

        blogPostService.deleteBlogPost(id);*/

        blogPost.setDeleted(true);
        blogPostService.saveBlogPost(blogPost);
        return "redirect:/blogpost";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @Autowired
    public void setBlogPostService(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }
}
