package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.dto.request.Like;
import KWU.likelion.GoldBalance.dto.request.MakeComment;
import KWU.likelion.GoldBalance.dto.response.AddLikeCount;
import KWU.likelion.GoldBalance.dto.response.CommentList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class CommentController implements CommentOperations{


    @Override
    public ResponseEntity<CommentList> getCommentsByPostId(Long postId) {
        return null;
    }

    @Override
    public ResponseEntity<MakeComment> getChildComments(Long postId, Long selectSide, Long commentId) {
        return null;
    }

    @Override
    public ResponseEntity<MakeComment> submitParentComments(Long postId, Long selectSide, MakeComment makeCommentDto) {
        return null;
    }

    @Override
    public ResponseEntity<MakeComment> submitChildComments(Long postId, Long selectSide, Long commentId, Long parentId, MakeComment makeCommentDto) {
        return null;
    }

    @Override
    public ResponseEntity<AddLikeCount> updateLikeCount(Long postId, Long commentId, Like likeDto) {
        return null;
    }
}
