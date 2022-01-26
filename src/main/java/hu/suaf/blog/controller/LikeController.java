package hu.suaf.blog.controller;


import hu.suaf.blog.model.*;
import hu.suaf.blog.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/like")
@Slf4j
public class LikeController {


    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private BlogPostCommentService blogPostCommentService;

    @Autowired
    private BlogPostCommentLikeService blogPostCommentLikeService;

    private BlogPostLikeService blogPostLikeService;

    @Autowired
    private BlogUserService blogUserService;

    @GetMapping("/create/post/{postid}")
    public String showBlogPostLikeCreateForm(@PathVariable long postid, BlogPostLike like, Model model){
        BlogPost blogpost = blogPostService.getBlogPostById(postid);
        model.addAttribute("blogpost", blogpost);
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        BlogPost currentPost = blogPostService.getBlogPostById(postid);
        model.addAttribute("currentUser", currentUser);
        like.setUser(currentUser);
        like.setPost(currentPost);

        //check if suer liked the post already
        for(BlogPostLike postLike : blogPostLikeService.getBlogPostLikes()){

            if(postLike.getPost().getId().equals(currentPost.getId()) && postLike.getUser().getId()==(currentUser.getId())){
                return "redirect:/";
            }
        }
        blogPostLikeService.saveBlogPostLike(like);
        return "redirect:/";
    }

    @GetMapping("/create/comment/{commentid}")
    public String showBlogPostCommentLikeCreateForm(@PathVariable long commentid, BlogPostCommentLike like, Model model){
        BlogPostComment blogPostComment = blogPostCommentService.getBlogPostCommentById(commentid);
        model.addAttribute("blogPostComment", blogPostComment);
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        BlogPostComment currentPostComment = blogPostCommentService.getBlogPostCommentById(commentid);
        model.addAttribute("currentUser", currentUser);
        like.setUser(currentUser);
        like.setComment(currentPostComment);

        //check if suer liked the post already
        for(BlogPostCommentLike commentLike : blogPostCommentLikeService.getBlogPostCommentLikes()){

            if(commentLike.getComment().getId().equals(currentPostComment.getId()) && commentLike.getUser().getId()==(currentUser.getId())){
                return "redirect:/";
            }
        }
        blogPostCommentLikeService.saveBlogPostCommentLike(like);
        return "redirect:/";
    }


    @GetMapping("/delete/post/{postid}")
    public String deleteBlogPostLike(@PathVariable long postid){
       /*for(BlogPostLike like : blogPostLikeService.getBlogPostLikes()){
            if(like.getUser().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName()) && like.getPost().getId().equals(postid)){
                blogPostLikeService.deleteBlogPostLike(like.getId());
            }
        }*/

        for(BlogPostLike like : blogPostLikeService.getBlogPostLikes()){
            if(like.getUser().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName()) && like.getPost().getId().equals(postid)){
                BlogPostLike likee = blogPostLikeService.getBlogPostLikeById(like.getId());
                likee.setDeleted(true);
                blogPostLikeService.saveBlogPostLike(likee);
            }
        }
        return "redirect:/";
    }

    @GetMapping("/delete/comment/{commentid}")
    public String deleteBlogPostCommentLike(@PathVariable long commentid){
        for(BlogPostCommentLike like : blogPostCommentLikeService.getBlogPostCommentLikes()){
            if(like.getUser().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName()) && like.getComment().getId().equals(commentid)){
                blogPostCommentLikeService.deleteBlogPostCommentLike(like.getId());
            }
        }
        return "redirect:/";
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @Autowired
    public void setBlogPostLikeService(BlogPostLikeService blogPostLikeService) {
        this.blogPostLikeService = blogPostLikeService;
    }
}
