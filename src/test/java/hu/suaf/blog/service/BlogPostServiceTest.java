package hu.suaf.blog.service;


import hu.suaf.blog.model.BlogPost;
import hu.suaf.blog.repository.BlogPostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


class ContactServiceTest {

    @InjectMocks
    private BlogPostService blogPostService;

    @Mock
    private BlogPostRepository blogPostRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        BlogPost blogPost = new BlogPost();
        blogPost.setId(1L);
        blogPost.setTitle("Title asd");
        blogPost.setTitle("Text asd");

        when(blogPostRepository.findById(anyLong())).thenReturn(Optional.of(blogPost));

    }

    @Test
    public void shouldReturnBlogPostById(){
    BlogPost blogPost = blogPostService.getBlogPostById(1L);

        assertThat(blogPost.getId()).isEqualTo(1L);
    }

}