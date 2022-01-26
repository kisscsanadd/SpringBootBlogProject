package hu.suaf.blog.controller;



import hu.suaf.blog.model.BlogPost;
import hu.suaf.blog.model.BlogPostCategory;
import hu.suaf.blog.model.BlogUser;
import hu.suaf.blog.service.BlogPostCategoryService;
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

@Controller
@RequestMapping("/categories")
@Slf4j
public class CategoriesController {

    @Autowired
    private BlogPostCategoryService blogPostCategoryService;

    @Autowired
    private BlogUserService blogUserService;

    @GetMapping
    public String listOfBlogPostCategories(Model model){
        model.addAttribute("categories", blogPostCategoryService.getBlogPostCategories());

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);

        return "categories";
    }

    @PostMapping
    public String listOfBlogPostCategoriesPost(Model model){
        model.addAttribute("categories", blogPostCategoryService.getBlogPostCategories());

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        return "categories";
    }
    @GetMapping("/create")
    public String showBlogPostCategoryCreateForm(BlogPostCategory category, Model model){
        model.addAttribute("category", category);
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        return "category-create";
    }

    @GetMapping("/edit/{id}")
    public String editBlogPostCategoryForm(@PathVariable long id, Model model){

        BlogPostCategory category = blogPostCategoryService.getBlogPostCategoryById(id);

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("category", category);
        return "category-create";
    }

    @PostMapping("/edit/{id}")
    public String editBlogCategoryPost(@Valid BlogPostCategory category, BindingResult result, Model model){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        if (result.hasErrors()) {
            return "category-create";
        }
        blogPostCategoryService.saveBlogPostCategory(category);

        return "redirect:/categories";
    }

    @PostMapping("/create")
    public String createBlogPostCategory(@Valid BlogPostCategory category, BindingResult result, Model model){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        BlogUser currentUser = blogUserService.getBlogUserByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("category", category);
        if (result.hasErrors()) {
            return "blogpost-create";
        }
        blogPostCategoryService.saveBlogPostCategory(category);
        return "redirect:/blogpost";
    }

    //soft delete
    @GetMapping("/delete/{id}")
    public String deleteBlogPostCategory(@PathVariable long id){
        BlogPostCategory category = blogPostCategoryService.getBlogPostCategoryById(id);
        category.setDeleted(true);
        blogPostCategoryService.saveBlogPostCategory(category);
        return "redirect:/categories";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
