package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Comment;
import KWU.likelion.GoldBalance.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
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
        // given
        // Comment 객체 생성
        Comment comment = new Comment();
        comment.setId(1);
        comment.setLikeCount(0);

        // CommentRepository의 save 메소드가 호출되면 미리 생성한 Comment 객체를 반환하도록 설정
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // when
        // createComment 메소드를 호출하고 반환값을 저장
        Comment createdComment = commentService.createComment(comment);

        // then
        assertEquals(comment.getId(), createdComment.getId());
        assertEquals(comment.getLikeCount(), createdComment.getLikeCount());
    }

    @Test
    public void testGetComment() {
        // given
        // Comment 객체 생성
        Comment comment = new Comment();
        comment.setId(1);

        // CommentRepository의 findById 메소드가 호출되면 미리 생성한 Comment 객체를 반환하도록 설정
        // anyInt()는 Mockito에서 제공하는 메소드로, 어떤 정수 값이든 상관없다는 것을 의미
        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(comment));

        // when
        Comment foundComment = commentService.getComment(1);

        // then
        assertEquals(comment.getId(), foundComment.getId());
    }

    @Test
    public void testLikeComment() {
        // given
        // Comment 객체 생성
        Comment comment = new Comment();
        comment.setId(1);
        comment.setLikeCount(0);

        // CommentRepository의 findById 메소드가 호출되면 미리 생성한 Comment 객체를 반환하도록 설정
        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(comment));

        // CommentRepository의 save 메소드가 호출될 때 미리 생성한 Comment 객체를 반환하도록 설정
        // likeComment 메소드가 null을 반환하는 문제 해결
        when(commentRepository.save(any(Comment.class))).thenAnswer(i -> i.getArguments()[0]);

        // when
        // likeComment 메소드를 호출하고 반환값을 저장
        Comment likedComment = commentService.likeComment(1);

        // then
        assertEquals(1, likedComment.getLikeCount());
    }

    @Test
    public void testGetAllComment() {
        // given
        // Comment 객체 생성
        Comment comment1 = new Comment();
        comment1.setId(1);

        Comment comment2 = new Comment();
        comment2.setId(2);

        List<Comment> expectedComments = Arrays.asList(comment1, comment2);

        // CommentRepository의 findAll 메소드가 호출되면 미리 생성한 Comment 객체들을 반환하도록 설정
        when(commentRepository.findAll()).thenReturn(expectedComments);

        // when
        // getAllComment 메소드를 호출하고 반환값을 저장
        List<Comment> actualComments = commentService.getAllComment();

        // then
        // 반환된 댓글 리스트가 예상한 댓글 리스트와 일치하는지 확인
        assertEquals(expectedComments, actualComments);
    }

    @Test
    public void testGetAllChildComment() {
        // given
        // Comment 객체 생성
        Comment parentComment = new Comment();
        parentComment.setId(1);

        Comment childComment1 = new Comment();
        childComment1.setId(2);
        childComment1.setParentCommentId(1);

        Comment childComment2 = new Comment();
        childComment2.setId(3);
        childComment2.setParentCommentId(1);

        List<Comment> expectedComments = Arrays.asList(childComment1, childComment2);

        // repository 메소드가 해당 자식 댓글 목록을 반환하도록 설정
        when(commentRepository.findByParentCommentId(anyInt())).thenReturn(expectedComments);

        // when
        // getAllChildComment 메소드를 호출하고 반환값을 저장
        List<Comment> actualComments = commentService.getAllChildComment(1);

        // then
        // 반환된 댓글 리스트가 예상한 댓글 리스트와 일치하는지 확인
        assertEquals(expectedComments, actualComments);
    }

    @Test
    public void testGetMostLikedCommentLeft() {
        // given
        // Comment 객체 생성
        // 왼쪽
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setLikeCount(10);
        comment1.setSideInfo(0);

        // 좋아요 가장 높은 comment
        Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setLikeCount(30);
        comment2.setSideInfo(0);

        Comment comment3 = new Comment();
        comment3.setId(3);
        comment3.setLikeCount(20);
        comment3.setSideInfo(0);

        // 오른쪽
        Comment comment4 = new Comment();
        comment4.setId(4);
        comment4.setLikeCount(40);
        comment4.setSideInfo(1);

        List<Comment> leftComments = Arrays.asList(comment1, comment2, comment3);

        // CommentRepository의 findBySideInfo 메소드가 호출되면 미리 생성한 Comment 객체들을 반환하도록 설정
        when(commentRepository.findBySideInfo(0)).thenReturn(leftComments);

        // when
        // getMostLikedCommentLeft 메소드를 호출하고 반환값을 저장
        Comment mostLikedComment = commentService.getMostLikedCommentLeft();

        // then
        // 반환된 댓글이 좋아요 수가 가장 많은 댓글인지 확인
        assertEquals(comment2, mostLikedComment);
    }

    @Test
    public void testGetMostLikedCommentRight() {
        // given
        // Comment 객체 생성
        // 오른쪽
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setLikeCount(10);
        comment1.setSideInfo(1);

        // 좋아요 가장 높은 comment
        Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setLikeCount(30);
        comment2.setSideInfo(1);

        Comment comment3 = new Comment();
        comment3.setId(3);
        comment3.setLikeCount(20);
        comment3.setSideInfo(1);

        // 왼쪽
        Comment comment4 = new Comment();
        comment4.setId(4);
        comment4.setLikeCount(40);
        comment4.setSideInfo(0);

        List<Comment> rightComments = Arrays.asList(comment1, comment2, comment3);

        // CommentRepository의 findBySideInfo 메소드가 호출되면 미리 생성한 Comment 객체들을 반환하도록 설정
        when(commentRepository.findBySideInfo(1)).thenReturn(rightComments);

        // when
        // getMostLikedCommentRight 메소드를 호출하고 반환값을 저장
        Comment mostLikedComment = commentService.getMostLikedCommentRight();

        // then
        // 반환된 댓글이 좋아요 수가 가장 많은 댓글인지 확인
        assertEquals(comment2, mostLikedComment);
    }
}