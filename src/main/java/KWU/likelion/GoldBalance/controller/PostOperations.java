package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.dto.request.MakePost;
import KWU.likelion.GoldBalance.dto.response.PostInfo;
import KWU.likelion.GoldBalance.dto.response.PostList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/default") //투표 게시물에 대한 controller
public interface PostOperations {

    @GetMapping("/comment_count") //처음에 모든 정보를 받아 투표의 list를 반환함 - 댓글 순
    ResponseEntity<PostList> getPostsSortedByCommentCount();

    @GetMapping("/vote_count") //처음에 모든 정보를 받아 투표의 list를 반환함 - 투표 순
    ResponseEntity<PostList> getPostsSortedByVoteCount();

    @GetMapping("/latest") //처음에 모든 정보를 받아 투표의 list를 반환함 - 시간 순
    ResponseEntity<PostList> getPostsSortedByTime();
    @GetMapping("/closest") // 5퍼 내로 차이나는 게시물을 불러올 수 있는 메서드
    ResponseEntity<PostList> getPostsbyCloseVote();
    @PostMapping("/make") //투표 게시물 저장
    ResponseEntity<String> submitPost(@RequestBody MakePost makePost);

    @GetMapping("{id}/select") //아이디데 대한 post 전달
    ResponseEntity<PostInfo> getPostById(@PathVariable String id);
}
