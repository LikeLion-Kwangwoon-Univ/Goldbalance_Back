package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Comment;
import KWU.likelion.GoldBalance.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    // 테스트 실행 전 Mock 객체를 초기화
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateComment() {
        // Comment 객체 생성
        Comment comment = new Comment();
        comment.setId(1);
        comment.setLikeCount(0);

        // CommentRepository의 save 메소드가 호출되면 미리 생성한 Comment 객체를 반환하도록 설정
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // createComment 메소드를 호출하고 반환값을 저장
        Comment createdComment = commentService.createComment(comment);

        assertEquals(comment.getId(), createdComment.getId());
        assertEquals(comment.getLikeCount(), createdComment.getLikeCount());
    }

    @Test
    public void testGetComment() {
        // Comment 객체 생성
        Comment comment = new Comment();
        comment.setId(1);

        // CommentRepository의 findById 메소드가 호출되면 미리 생성한 Comment 객체를 반환하도록 설정
        // anyInt()는 Mockito에서 제공하는 메소드로, 어떤 정수 값이든 상관없다는 것을 의미
        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(comment));

        Comment foundComment = commentService.getComment(1);

        assertEquals(comment.getId(), foundComment.getId());
    }

    @Test
    void likeComment() {
    }

    @Test
    void getAllComment() {
    }

    @Test
    void getAllChildComment() {
    }

    @Test
    void getMostLikedCommentLeft() {
    }

    @Test
    void getMostLikedCommentRight() {
    }
}