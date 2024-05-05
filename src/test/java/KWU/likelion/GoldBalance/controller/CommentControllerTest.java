package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.domain.Comment;
import KWU.likelion.GoldBalance.dto.response.CommentList;
import KWU.likelion.GoldBalance.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentControllerTest {


        @Mock
        private CommentService commentService;

        @InjectMocks
        private CommentController commentController;


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
            }

            verify(commentService, times(1)).getAllComment();
        }


}
