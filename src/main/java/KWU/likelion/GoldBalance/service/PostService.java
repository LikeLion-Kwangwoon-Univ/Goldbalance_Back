package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Post;

import java.util.List;

public interface PostService {

    // 게시물 생성 메소드
    Post createPost(Post post);

    // 게시물 조회 메소드
    Post getPost(int postId);

    // 모든 게시물 조회 메소드 (최신순)
    List<Post> getAllPostByNewest();

    // 모든 게시물 조회 메소드 (댓글이 가장 많은 게시물 순서)
    List<Post> getAllPostByMostComments();

    // 모든 게시물 조회 메소드 (투표가 가장 많은 게시물 순서)
    List<Post> getAllPostByMostVotes();

    // 모든 게시물 조회 메소드 (투표 비율이 5%(?) 이내로 차이가 나는 게시물)
    List<Post> getAllPostByCloseVote();
}
