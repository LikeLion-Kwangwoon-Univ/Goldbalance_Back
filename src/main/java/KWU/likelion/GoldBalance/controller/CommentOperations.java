package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.dto.request.Like;
import KWU.likelion.GoldBalance.dto.request.MakeComment;
import KWU.likelion.GoldBalance.dto.response.AddLikeCount;
import KWU.likelion.GoldBalance.dto.response.CommentList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/goldbalance/select")
public interface CommentOperations {

    // 특정 게시물의 댓글 전체 조회
    @GetMapping("/{postId}/comment")
    ResponseEntity<CommentList> getCommentsByPostId(@PathVariable Long postId);

    // comment_id의 parent_id 조회
    // (parent_id == -1 이면 댓글, parent_id != -1 이면 대댓글)
    @GetMapping("/{postId}/{selectSide}/comment/{commentId}")
    ResponseEntity<MakeComment> getChildComments(@PathVariable Long postId, @PathVariable Long selectSide, @PathVariable Long commentId);

    // 댓글 작성
    @PostMapping("/{postId}/{selectSide}/comment")
    ResponseEntity<MakeComment> submitParentComments(@PathVariable Long postId, @PathVariable Long selectSide, @RequestBody MakeComment makeCommentDto);

    // parent_id 에 대한 대댓글 작성
    @PostMapping("/{postId}/{selectSide}/comment/{commentId}/{parentId}")
    ResponseEntity<MakeComment> submitChildComments(@PathVariable Long postId, @PathVariable Long selectSide,
                                                    @PathVariable Long commentId,@PathVariable Long parentId,
                                                    @RequestBody MakeComment makeCommentDto);

    // 좋아요 눌렸을 때, 좋아요 수 update
    @PostMapping("/{postId}/{selectSide}/comment/{commentId}")
    ResponseEntity<AddLikeCount> updateLikeCount(@PathVariable Long postId,@PathVariable Long commentId, @RequestBody Like likeDto);

}
