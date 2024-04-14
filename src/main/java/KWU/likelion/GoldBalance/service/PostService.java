package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Post;

import java.util.List;

public interface PostService {
    // 게시물 생성 메소드
    Post createPost(Post post);
    // 게시물 조회 메소드
    Post getPost(Long postId);
    // 모든 게시물 조회 메소드
    List<Post> getAllPost();
}
