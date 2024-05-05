package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.domain.Comment;
import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.dto.request.Like;
import KWU.likelion.GoldBalance.dto.request.MakeComment;
import KWU.likelion.GoldBalance.dto.response.AddLikeCount;
import KWU.likelion.GoldBalance.dto.response.CommentList;
import KWU.likelion.GoldBalance.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class CommentController implements CommentOperations{

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //Postid 로 모든 댓글 가져 오기 (10개씩?)
    @Override
    public ResponseEntity<CommentList> getCommentsByPostId(Long postId) {
        List<Comment> postComments = commentService.getAllComment()
                .stream()
                .filter(comment -> Long.valueOf(comment.getPostId()).equals(postId))
                .collect(Collectors.toList());
        CommentList commentList = new CommentList();
        commentList.setCommentList(postComments);
        return ResponseEntity.ok(commentList);
    }

    @Override
    public ResponseEntity<MakeComment> getChildComments(Long postId, Long selectSide, Long commentId) {

        return null;
    }

    @Override
    public ResponseEntity<MakeComment> submitParentComment(Long postId, Long selectSide, MakeComment makeCommentDto) {


        return null;
    }

    @Override
    public ResponseEntity<MakeComment> submitChildComment(Long postId, Long selectSide, Long commentId, Long parentId, MakeComment makeCommentDto) {

        return null;
    }

    @Override
    public ResponseEntity<AddLikeCount> updateLikeCount(Long postId, Long commentId, Like likeDto) {

        return null;
    }
}
