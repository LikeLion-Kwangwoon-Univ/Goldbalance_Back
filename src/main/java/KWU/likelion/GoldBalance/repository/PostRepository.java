package KWU.likelion.GoldBalance.repository;

import KWU.likelion.GoldBalance.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<Post, Integer> {
}
