package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Comment;

import java.util.List;

public interface CommentService {

    // 댓글 생성 메소드
    Comment createComment(Comment comment);

    // 댓글 조회 메소드
    Comment getComment(int commentId);

    // 댓글 좋아요 메소드
    Comment likeComment(int commentId);

    // 댓글 좋아요 취소 메소드
    Comment unlikeComment(int commentId);

    // post_id에 해당하는 모든 댓글 조회 메소드
    List<Comment> getAllComment(int postId);

    // 특정 댓글의 모든 대댓글 조회 메소드
    List<Comment> getAllChildComment(int commentId);

    // 아래 두 메소드: likeCount와 sideInfo 이용해서 구현
    // 왼쪽 측면에서 가장 좋아요가 많은 댓글 조회 메소드
    Comment getMostLikedCommentLeft();

    // 오른쪽 측면에서 가장 좋아요가 많은 댓글 조회 메소드
    Comment getMostLikedCommentRight();
}
