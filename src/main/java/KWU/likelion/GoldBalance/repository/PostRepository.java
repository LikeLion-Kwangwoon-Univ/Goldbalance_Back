package KWU.likelion.GoldBalance.repository;

import KWU.likelion.GoldBalance.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByOrderByCreatedDateTimeDesc(); // 게시물을 생성일자(CreatedDateTime) 기준으로 내림차순 정렬하여 반환

    @Query("SELECT p FROM Post p ORDER BY (SELECT COUNT(c) FROM Comment c WHERE c.post = p) DESC")
    List<Post> findAllByOrderByCommentsCountDesc();
}
