package hu.suaf.blog.controller;

import hu.suaf.blog.model.*;
import hu.suaf.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class HomeController {

    private final BlogPostService blogPostService;

    @Autowired
    private BlogPostCommentService blogPostCommentService;

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private BlogPostLikeService blogPostLikeService;

    @Autowired
    private BlogPostCommentLikeService blogPostCommentLikeService;

    @Autowired
    public HomeController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @RequestMapping(value = {"/", "/search"}, method = RequestMethod.GET)
    public String listAllPosts(Model model, String title) {

        Collection<BlogPost> allPosts = this.blogPostService.getBlogPosts();

        Collection<BlogPost> posts = new ArrayList<>();

        if(title != null){
            for (BlogPost blogPost:allPosts) {
                String postTitle = blogPost.getTitle().toLowerCase();
                if(postTitle.contains(title.toLowerCase())){
                    posts.add(blogPost);
                }
            }
        }else{
            posts = allPosts;
        }

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);

        for(BlogPost post: posts){
            List<BlogPostComment> comments = blogPostCommentService.getBlogPostComments();
            List<BlogPostComment> commentsForBlogPost = new ArrayList<>();
            for(BlogPostComment comment : comments){
                if(comment.getPost()==post){
                    commentsForBlogPost.add(comment);
                }
            }
            post.setComments(commentsForBlogPost);
        }

        List<Long> postIdsLikedByCurrentUser = new ArrayList<>();

        for (BlogPostLike like : blogPostLikeService.getBlogPostLikes()){
            if(like.getUser().getUsername().equals(currentUserName)){
                if(like.getPost() != null){
                    postIdsLikedByCurrentUser.add(like.getPost().getId());
                }
            }
        }

        List<Long> postCommentIdsLikedByCurrentUser = new ArrayList<>();
        for (BlogPostCommentLike like : blogPostCommentLikeService.getBlogPostCommentLikes()){
            if(like.getUser().getUsername().equals(currentUserName)){
                if(like.getComment() != null){
                    postCommentIdsLikedByCurrentUser.add(like.getComment().getId());
                }
            }
        }

        model.addAttribute("likedpostcommentids", postCommentIdsLikedByCurrentUser);
        model.addAttribute("likedpostids", postIdsLikedByCurrentUser);
        model.addAttribute("blogposts", posts);
        model.addAttribute("currentUser", currentUser);

        return "home";
    }

    @RequestMapping(value = {"/descending"}, method = RequestMethod.GET)
    public String listAllPostsDescending(Model model) {

        Collection<BlogPost> allPosts = this.blogPostService.getBlogPostsDateDescending();

        Collection<BlogPost> posts = new ArrayList<>();

            posts = allPosts;

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);

        for(BlogPost post: posts){
            List<BlogPostComment> comments = blogPostCommentService.getBlogPostComments();
            List<BlogPostComment> commentsForBlogPost = new ArrayList<>();
            for(BlogPostComment comment : comments){
                if(comment.getPost()==post){
                    commentsForBlogPost.add(comment);
                }
            }
            post.setComments(commentsForBlogPost);
        }

        List<Long> postIdsLikedByCurrentUser = new ArrayList<>();

        for (BlogPostLike like : blogPostLikeService.getBlogPostLikes()){
            if(like.getUser().getUsername().equals(currentUserName)){
                if(like.getPost() != null){
                    postIdsLikedByCurrentUser.add(like.getPost().getId());
                }
            }
        }

        List<Long> postCommentIdsLikedByCurrentUser = new ArrayList<>();
        for (BlogPostCommentLike like : blogPostCommentLikeService.getBlogPostCommentLikes()){
            if(like.getUser().getUsername().equals(currentUserName)){
                if(like.getComment() != null){
                    postCommentIdsLikedByCurrentUser.add(like.getComment().getId());
                }
            }
        }

        model.addAttribute("likedpostcommentids", postCommentIdsLikedByCurrentUser);
        model.addAttribute("likedpostids", postIdsLikedByCurrentUser);
        model.addAttribute("blogposts", posts);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("ascending", false);

        return "home";
    }

    @RequestMapping(value = {"/ascending"}, method = RequestMethod.GET)
    public String listAllPostsAscending(Model model) {

        Collection<BlogPost> allPosts = this.blogPostService.getBlogPostsDateAscending();
        Collection<BlogPost> allPostssdfsdf = this.blogPostService.getBlogPostsDateDescending();

        Collection<BlogPost> posts = new ArrayList<>();

        posts = allPosts;

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);

        for(BlogPost post: posts){
            List<BlogPostComment> comments = blogPostCommentService.getBlogPostComments();
            List<BlogPostComment> commentsForBlogPost = new ArrayList<>();
            for(BlogPostComment comment : comments){
                if(comment.getPost()==post){
                    commentsForBlogPost.add(comment);
                }
            }
            post.setComments(commentsForBlogPost);
        }

        List<Long> postIdsLikedByCurrentUser = new ArrayList<>();

        for (BlogPostLike like : blogPostLikeService.getBlogPostLikes()){
            if(like.getUser().getUsername().equals(currentUserName)){
                if(like.getPost() != null){
                    postIdsLikedByCurrentUser.add(like.getPost().getId());
                }
            }
        }

        List<Long> postCommentIdsLikedByCurrentUser = new ArrayList<>();
        for (BlogPostCommentLike like : blogPostCommentLikeService.getBlogPostCommentLikes()){
            if(like.getUser().getUsername().equals(currentUserName)){
                if(like.getComment() != null){
                    postCommentIdsLikedByCurrentUser.add(like.getComment().getId());
                }
            }
        }

        model.addAttribute("likedpostcommentids", postCommentIdsLikedByCurrentUser);
        model.addAttribute("likedpostids", postIdsLikedByCurrentUser);
        model.addAttribute("blogposts", posts);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("ascending", true);

        return "home";
    }

    @PostMapping("/search")
    public String listAllPostsByDetailedSearch(SearchBody searchBody,Model model) {

        Collection<BlogPost> posts = this.blogPostService.getBlogPosts();

        if(searchBody.getTitle() != null && !searchBody.getTitle().isEmpty()){
            Collection<BlogPost> postsByTitle = new ArrayList<>();
            for (BlogPost blogPost:posts) {
                String postTitle = blogPost.getTitle().toLowerCase();
                if(postTitle.contains(searchBody.getTitle().toLowerCase())){
                    postsByTitle.add(blogPost);
                }
            }
            posts = postsByTitle;
        }

        if(searchBody.getText() != null && !searchBody.getText().isEmpty()){
            Collection<BlogPost> postsByText = new ArrayList<>();
            for (BlogPost blogPost:posts) {
                String postText = blogPost.getText().toLowerCase();
                if(postText.contains(searchBody.getText().toLowerCase())){
                    postsByText.add(blogPost);
                }
            }
            posts = postsByText;
        }

        if(searchBody.getAuthor() != null && !searchBody.getAuthor() .isEmpty()){
            Collection<BlogPost> postsByAuthor = new ArrayList<>();
            for (BlogPost blogPost:posts) {
                if(blogPost.getUser() != null){
                    String postAuthor = blogPost.getUser().getUsername().toLowerCase();
                    if(postAuthor.contains(searchBody.getAuthor() .toLowerCase())){
                        postsByAuthor.add(blogPost);
                    }
                }
            }
            posts = postsByAuthor;
        }

        if(searchBody.getBeginDate() != null){
            Collection<BlogPost> postsByBeginDate = new ArrayList<>();
            for (BlogPost blogPost:posts) {
                if(blogPost.getCreatedAt() != null){
                    Date postCreatedAt = blogPost.getCreatedAt();
                    if(postCreatedAt.after(searchBody.getBeginDate())){
                        postsByBeginDate.add(blogPost);
                    }
                }
            }
            posts = postsByBeginDate;
        }

        if(searchBody.getEndDate() != null){
            Collection<BlogPost> postsByEndDate = new ArrayList<>();
            for (BlogPost blogPost:posts) {
                if(blogPost.getCreatedAt() != null){
                    Date postCreatedAt = blogPost.getCreatedAt();
                    if(postCreatedAt.before(searchBody.getEndDate())){
                        postsByEndDate.add(blogPost);
                    }
                }
            }
            posts = postsByEndDate;
        }

        String currentUserName = "anonymousUser";
        if(SecurityContextHolder.getContext().getAuthentication() != null){
            currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);

        for(BlogPost post: posts){
            List<BlogPostComment> comments = blogPostCommentService.getBlogPostComments();
            List<BlogPostComment> commentsForBlogPost = new ArrayList<>();
            for(BlogPostComment comment : comments){
                if(comment.getPost()==post){
                    commentsForBlogPost.add(comment);
                }
            }
            post.setComments(commentsForBlogPost);
        }

        List<Long> postIdsLikedByCurrentUser = new ArrayList<>();

        for (BlogPostLike like : blogPostLikeService.getBlogPostLikes()){
            if(like.getUser().getUsername().equals(currentUserName)){
                if(like.getPost() != null){
                    postIdsLikedByCurrentUser.add(like.getPost().getId());
                }
            }
        }

        List<Long> postCommentIdsLikedByCurrentUser = new ArrayList<>();
        for (BlogPostCommentLike like : blogPostCommentLikeService.getBlogPostCommentLikes()){
            if(like.getUser().getUsername().equals(currentUserName)){
                if(like.getComment() != null){
                    postCommentIdsLikedByCurrentUser.add(like.getComment().getId());
                }
            }
        }

        model.addAttribute("likedpostcommentids", postCommentIdsLikedByCurrentUser);
        model.addAttribute("likedpostids", postIdsLikedByCurrentUser);
        model.addAttribute("blogposts", posts);
        model.addAttribute("currentUser", currentUser);
        return "home";
    }

    @GetMapping("/advanced-search")
    public String showAdvancedSearch(Model model, SearchBody searchBody){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        return "blogpost-advanced-search";
    }

    @PostMapping("/")
    public String listAllPostsPost(Model model) {

        Collection<BlogPost> posts = this.blogPostService.getBlogPosts();

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);

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

        model.addAttribute("blogposts", posts);
        model.addAttribute("currentUser", currentUser);

        return "home";
    }

}