package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.dto.request.Like;
import KWU.likelion.GoldBalance.dto.request.MakeComment;
import KWU.likelion.GoldBalance.dto.response.CommentList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/default")
public interface CommentOperations {
    //부모 댓글 조회
    @GetMapping("/{postId}/comment")
    ResponseEntity<CommentList> getParentCommentsByPostId(@PathVariable Long postId);

    //자식 댓글 조회
    @GetMapping("/{postId}/recomment/{commentId}")
    ResponseEntity<CommentList> getChildCommentsPostIdAndCommentId(@PathVariable Long postId, @PathVariable Long commentId);

    //댓글 등록
    @PostMapping("/{postId}/comment/add")
    ResponseEntity<CommentList> postComment(@RequestBody MakeComment makeCommentDto, @PathVariable Long postId);
    //대댓글 등록
    @PostMapping("/{postId}/recomment/add")
    ResponseEntity<CommentList> postRecomment(@RequestBody MakeComment makeCommentDto,@PathVariable Long postId);

    // 좋아요 눌렸을 때, 좋아요 수 update
    @PostMapping("{postId}/comment/{commentId}/liked/{like}")
    ResponseEntity<String> postLikeCount( @RequestBody Like likeDto,@PathVariable Long postId,@PathVariable Long commentId,@PathVariable Long like);

}
