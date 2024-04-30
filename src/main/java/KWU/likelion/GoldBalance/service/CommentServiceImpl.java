package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Comment;
import KWU.likelion.GoldBalance.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public List<Comment> getAllComment() {
        return null;
    }

    @Override
    public List<Comment> getAllChildComment(int commentId) {
        return null;
    }

    @Override
    public Comment getMostLikedCommentLeft() {
        return null;
    }

    @Override
    public Comment getMostLikedCommentRight() {
        return null;
    }
}
