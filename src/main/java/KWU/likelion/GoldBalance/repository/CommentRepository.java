package KWU.likelion.GoldBalance.repository;

import KWU.likelion.GoldBalance.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByParentCommentId(int parentCommentId);
    List<Comment> findBySideInfo(int sideInfo);
}
