package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    // 게시물 생성 메소드
    Post createPost(Post post);

    // 게시물 조회 메소드
    Post getPost(int postId);

    // 모든 게시물 조회 메소드 (최신순)
    // page: 페이지 번호 (0부터 시작)
    // size: 한 페이지에 표시할 게시물 수
    Page<Post> getAllPostByNewest(int page, int size);

    // 모든 게시물 조회 메소드 (투표가 가장 많은 게시물 순서)
    List<Post> getAllPostByMostVotes(int page, int size);

    // 모든 게시물 조회 메소드 (투표 비율이 5%(?) 이내로 차이가 나는 게시물)
    List<Post> getAllPostByCloseVote(int page, int size);
}
