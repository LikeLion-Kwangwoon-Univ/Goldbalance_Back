package KWU.likelion.GoldBalance.repository;

import KWU.likelion.GoldBalance.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
