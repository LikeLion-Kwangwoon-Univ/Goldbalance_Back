package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.domain.Comment;
import KWU.likelion.GoldBalance.dto.request.MakeComment;
import KWU.likelion.GoldBalance.dto.response.CommentList;
import KWU.likelion.GoldBalance.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    // postId 에 해당하는 댓글 조회
    @Test
    public void testGetCommentsByPostId() {
        // Arrange
        Long postId = 1L;
        List<Comment> comments=new ArrayList<>();

        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setPostId(postId.intValue());
        comment1.setPassword("pw1");
        comment1.setContent("post1,comment1");
        comment1.setSideInfo(0);
        comment1.setParentCommentId(-1);
        comments.add(comment1);

        Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setPostId(postId.intValue());
        comment2.setPassword("pw2");
        comment2.setContent("post1,comment2");
        comment2.setSideInfo(0);
        comment2.setParentCommentId(-1);
        comments.add(comment2);


        when(commentService.getAllComment()).thenReturn(comments);

        // Act
        ResponseEntity<CommentList> responseEntity = this.commentController.getCommentsByPostId(postId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CommentList commentList = responseEntity.getBody();
        assert commentList != null;
        List<Comment> returnedComments = commentList.getCommentList();
        assertEquals(comments.size(), returnedComments.size());
        for (int i = 0; i < comments.size(); i++) {
            Comment returnedComment = returnedComments.get(i);
            assertEquals(postId, returnedComment.getPostId());
            System.out.println(returnedComment);
        }

        verify(commentService, times(1)).getAllComment();
    }

    // 대댓글 입력
    @Test
    public void testSubmitCommentsOfComment() {

        // Mock 객체 생성
        CommentService commentService = mock(CommentService.class);
//        CommentRepository commentRepository = mock(CommentRepository.class);

        // Controller 생성 및 Mock 객체 주입
        CommentController commentController = new CommentController(commentService);

        // 대댓글을 추가 할 테스트용 데이터 설정
        Long postId = 1L;
        int selectSide = 0;
        int parentCommentId = 2;

        //추가할 댓글(입력된 댓글)
        MakeComment makeCommentDto = new MakeComment();
        makeCommentDto.setPostId(postId.intValue());
        makeCommentDto.setPassword("pw2-comment");
        makeCommentDto.setContent("This is a new test comment.");
        makeCommentDto.setSideInfo(selectSide);
        makeCommentDto.setParentCommentId(parentCommentId); // 부모 댓글 postId

        // 존재하는 댓글 (makeCommentDto.ParentCommentId != savedComment.Id)
        Comment savedComment1 = new Comment();
        savedComment1.setId(1);
        savedComment1.setPostId(1);
        savedComment1.setParentCommentId(-1);
        savedComment1.setPassword("pw1");
        savedComment1.setContent("comment");
        savedComment1.setSideInfo(0);

        // 존재하는 댓글 (makeCommentDto.ParentCommentId == savedComment.Id)
        Comment savedComment2 = new Comment();
        savedComment2.setId(parentCommentId);
        savedComment2.setPostId(1);
        savedComment2.setParentCommentId(-1);
        savedComment2.setPassword("pw2");
        savedComment2.setContent("parent comment");
        savedComment2.setSideInfo(0);

        // 새로 작성된 대댓글
        Comment newComment = new Comment();
        newComment.setId(3); // 새로 작성된 댓글의 ID
        newComment.setPostId(makeCommentDto.getPostId());
        newComment.setParentCommentId(makeCommentDto.getParentCommentId()); // 부모 댓글 postId
        newComment.setPassword(makeCommentDto.getPassword());
        newComment.setContent(makeCommentDto.getContent());
        newComment.setSideInfo(makeCommentDto.getSideInfo());

        // commentService.getComment()의 반환 값 설정
        // (makeCommentDto.ParentCommentId == savedComment.Id)
        when(commentService.getComment(makeCommentDto.getParentCommentId())).thenReturn(savedComment2);
        // getAllComment() 메서드의 반환 값 설정
        List<Comment> allComments = new ArrayList<>();
        allComments.add(savedComment1);
        allComments.add(savedComment2);
        when(commentService.getAllComment()).thenReturn(allComments);
        // Mock 객체의 동작 설정
        when(commentService.createComment(any(Comment.class))).thenReturn(newComment);


        // 테스트 수행
        ResponseEntity<CommentList> response = commentController.submitComment((long) makeCommentDto.getPostId(), makeCommentDto.getSideInfo(), makeCommentDto);

        System.out.println("요청: " + makeCommentDto);
        System.out.println("응답: " + response.getBody());
        // 결과 검증
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // createComment 메서드가 올바른 Comment 객체를 인자로 호출되었는지 검증
        ArgumentCaptor<Comment> argument = ArgumentCaptor.forClass(Comment.class);
        verify(commentService).createComment(argument.capture());
        assertEquals(makeCommentDto.getContent(), argument.getValue().getContent());
        assertEquals(makeCommentDto.getPassword(), argument.getValue().getPassword());
        assertEquals(makeCommentDto.getSideInfo(), argument.getValue().getSideInfo());
        assertEquals(postId, Long.valueOf(argument.getValue().getPostId()));

        assertEquals(Long.valueOf(savedComment2.getId()), Long.valueOf(argument.getValue().getParentCommentId()));
    }

    //최상위 댓글 입력, 해당되는 postId의 root comment로 잘 입력 되는지 test
    @Test
    public void testSubmitRootComment() {

        // Mock 객체 생성
        CommentService commentService = mock(CommentService.class);
//        CommentRepository commentRepository = mock(CommentRepository.class);

        // Controller 생성 및 Mock 객체 주입
        CommentController commentController = new CommentController(commentService);

        // 댓글을 추가 할 테스트용 데이터 설정
        Long postId = 1L;
        int selectSide = 1;//오른쪽
        int parentCommentId = -1;

        //추가할 댓글(입력된 댓글)
        MakeComment makeCommentDto = new MakeComment();
        makeCommentDto.setPostId(postId.intValue());
        makeCommentDto.setPassword("pw3");
        makeCommentDto.setContent("This is a new test comment.");
        makeCommentDto.setSideInfo(selectSide);
        makeCommentDto.setParentCommentId(parentCommentId); // 부모 댓글 postId

        // 존재하는 댓글 (postId 같음)
        Comment savedComment1 = new Comment();
        savedComment1.setId(1);
        savedComment1.setPostId(1);
        savedComment1.setParentCommentId(-1);
        savedComment1.setPassword("pw1");
        savedComment1.setContent("comment");
        savedComment1.setSideInfo(1);

        // 존재하는 댓글 (postId 다름)
        Comment savedComment2 = new Comment();
        savedComment2.setId(2);
        savedComment2.setPostId(2);
        savedComment2.setParentCommentId(-1);
        savedComment2.setPassword("pw2");
        savedComment2.setContent("not equal postId");
        savedComment2.setSideInfo(0);

        // 새로 작성된 대댓글
        Comment newComment = new Comment();
        newComment.setId(3); // 새로 작성된 댓글의 ID
        newComment.setPostId(makeCommentDto.getPostId());
        newComment.setParentCommentId(makeCommentDto.getParentCommentId()); // 부모 댓글 postId
        newComment.setPassword(makeCommentDto.getPassword());
        newComment.setContent(makeCommentDto.getContent());
        newComment.setSideInfo(makeCommentDto.getSideInfo());

        // commentService.getComment()의 반환 값 설정
        // (makeCommentDto.ParentCommentId == savedComment.Id)
        when(commentService.getComment(makeCommentDto.getParentCommentId())).thenReturn(savedComment2);
        // getAllComment() 메서드의 반환 값 설정
        List<Comment> allComments = new ArrayList<>();
        allComments.add(savedComment1);
        allComments.add(savedComment2);
        when(commentService.getAllComment()).thenReturn(allComments);
        // Mock 객체의 동작 설정
        when(commentService.createComment(any(Comment.class))).thenReturn(newComment);


        // 테스트 수행
        ResponseEntity<CommentList> response = commentController.submitComment((long) makeCommentDto.getPostId(), makeCommentDto.getSideInfo(), makeCommentDto);

        System.out.println("요청: " + makeCommentDto);
        System.out.println("응답: " + response.getBody());
        // 결과 검증
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // createComment 메서드가 올바른 Comment 객체를 인자로 호출되었는지 검증
        ArgumentCaptor<Comment> argument = ArgumentCaptor.forClass(Comment.class);
        verify(commentService).createComment(argument.capture());
        assertEquals(makeCommentDto.getContent(), argument.getValue().getContent());
        assertEquals(makeCommentDto.getPassword(), argument.getValue().getPassword());
        assertEquals(makeCommentDto.getSideInfo(), argument.getValue().getSideInfo());
        assertEquals(postId, Long.valueOf(argument.getValue().getPostId()));

        assertEquals(Long.valueOf(savedComment1.getPostId()),Long.valueOf(argument.getValue().getPostId()));
        assertNotEquals(Long.valueOf(savedComment2.getPostId()),Long.valueOf(argument.getValue().getPostId()));

        assertNotEquals(Long.valueOf(savedComment1.getId()), Long.valueOf(argument.getValue().getParentCommentId()));
        assertNotEquals(Long.valueOf(savedComment2.getId()), Long.valueOf(argument.getValue().getParentCommentId()));
    }

}



