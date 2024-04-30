package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Comment;
import KWU.likelion.GoldBalance.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(Comment comment) {
        // save 메소드를 사용하여 Comment 객체를 데베에 저장하고 반환
        return commentRepository.save(comment);
    }

    @Override
    public Comment getComment(int commentId) {
        // commentId에 해당하는 Comment 객체를 데베에서 조회 -> 없다면 예외처리
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
    }

    @Override
    public Comment likeComment(int commentId) {
        // commentId에 해당하는 Comment객체의 좋아요 수를 1 증가하고 저장
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        comment.setLikeCount(comment.getLikeCount() + 1);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComment() {
        // 모든 Comment 객체를 조회하여 List<Comment>로 반환
        return (List<Comment>) commentRepository.findAll();
    }

    @Override
    public List<Comment> getAllChildComment(int commentId) {
        // parentCommentId가 commentId인 Comment 객체를 조회하여 List<Comment>로 반환
        return commentRepository.findByParentCommentId(commentId);
    }

    @Override
    public Comment getMostLikedCommentLeft() {
        // sideInfo가 0(왼쪽)인 Comment 객체 중 likeCount가 가장 큰 객체를 조회하여 반환
        // Stream API를 사용하여 max 메소드를 이용, Comparator를 사용하여 likeCount를 기준으로 비교
        return commentRepository.findBySideInfo(0).stream()
                .max(Comparator.comparingInt(Comment::getLikeCount))
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
    }

    @Override
    public Comment getMostLikedCommentRight() {
        // sideInfo가 1(오른쪽)인 Comment 객체로 getMostLikedCommentLeft()와 동일한 방법으로 조회
        return commentRepository.findBySideInfo(1).stream()
                .max(Comparator.comparingInt(Comment::getLikeCount))
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
    }
}
