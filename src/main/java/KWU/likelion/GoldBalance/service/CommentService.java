package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Comment;

import java.util.List;

public interface CommentService {
    // 댓글 생성 메소드
    Comment createComment(Comment comment);
    // 댓글 조회 메소드
    Comment getComment(Long commentId);
    // 모든 댓글 조회 메소드
    List<Comment> getAllComment();
    // 댓글 좋아요 메소드
    Comment likeComment(Long commentId);
    // 특정 댓글의 모든 대댓글 조회

    // 왼쪽 측면에서 가장 좋아요가 많은 댓글
    // 왼쪽 측면에서 가장 좋아요가 많은 댓글
}
