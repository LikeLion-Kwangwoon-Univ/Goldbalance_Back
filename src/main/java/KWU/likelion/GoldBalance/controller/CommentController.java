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

    //Postid 로 모든 댓글 가져 오기 (10개씩? & 대댓글을 리스트로 다 보여줄 것인지 따로 처리해 줄 것인지?)
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
    public ResponseEntity<CommentList> submitComment(Long postId, int selectSide, MakeComment makeCommentDto) {

        // 기존의 댓글 리스트를 가져옴
        List<Comment> postComments = commentService.getAllComment()
                .stream()
                .filter(comment -> Long.valueOf(comment.getPostId()).equals(postId)&& comment.getSideInfo() == selectSide)
                //postId 가 같고 selectSide 가 같은 것 filtering
                .collect(Collectors.toList());

        CommentList commentList = new CommentList();
        commentList.setCommentList(postComments); // 기존의 댓글 리스트를 설정

        if (makeCommentDto.getParentCommentId() == -1) {
            // 부모 댓글 작성
            Comment comment = new Comment();
            comment.setPostId(postId.intValue());
            comment.setPassword(makeCommentDto.getPassword());
            comment.setContent(makeCommentDto.getContent());
            comment.setSideInfo(makeCommentDto.getSideInfo());
            comment.setParentCommentId(-1); // 부모 댓글의 경우 parentId는 -1

            Comment savedComment = commentService.createComment(comment);

            // 새로운 댓글을 기존의 CommentList에 추가합니다.
            commentList.getCommentList().add(savedComment);
        } else {
            // 대댓글 작성
            Comment parentComment = commentService.getComment(makeCommentDto.getParentCommentId());
            if (parentComment != null && Long.valueOf(parentComment.getPostId()).equals(postId)) {
                // 부모 댓글이 존재하고, 해당 부모 댓글이 요청된 게시물에 속한 경우에만 대댓글 작성
                Comment comment = new Comment();
                comment.setPostId(postId.intValue());
                comment.setPassword(makeCommentDto.getPassword());
                comment.setContent(makeCommentDto.getContent());
                comment.setSideInfo(makeCommentDto.getSideInfo());
                comment.setParentCommentId(makeCommentDto.getParentCommentId()); // 부모 댓글의 postId를 parentId로 설정

                Comment newComment = commentService.createComment(comment);

                // 새로운 댓글을 기존의 CommentList에 추가
                commentList.getCommentList().add(newComment);
            } else {
                // 부모 댓글이 없거나 요청된 게시물에 속하지 않는 경우 예외 처리
                return ResponseEntity.badRequest().build();
            }
        }

        // 새로운 댓글이 추가된 CommentList를 ResponseEntity로 반환
        return ResponseEntity.ok(commentList);
    }

    @Override
    public ResponseEntity<AddLikeCount> updateLikeCount(Long postId, Long commentId, Like likeDto) {
        return null;
    }
}
