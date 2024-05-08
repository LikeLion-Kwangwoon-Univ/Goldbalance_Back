package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPost() {
        // Given
        Post post = new Post();
        post.setPostTitle("Test Title");
        post.setLeftSideTitle("Left Side Title");
        post.setRightSideTitle("Right Side Title");

        when(postRepository.save(any(Post.class))).thenReturn(post);

        // When
        Post createdPost = postService.createPost(post);

        // Then
        assertEquals(post.getPostTitle(), createdPost.getPostTitle());
        assertEquals(post.getLeftSideTitle(), createdPost.getLeftSideTitle());
        assertEquals(post.getRightSideTitle(), createdPost.getRightSideTitle());
    }

    @Test
    void getPost() {
    }

    @Test
    void getAllPostByNewest() {
    }

    @Test
    void getAllPostByMostComments() {
    }

    @Test
    void getAllPostByMostVotes() {
    }

    @Test
    void getAllPostByCloseVote() {
    }
}