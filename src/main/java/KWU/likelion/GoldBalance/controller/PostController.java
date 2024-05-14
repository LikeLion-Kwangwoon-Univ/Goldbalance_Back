package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.dto.request.MakePost;
import KWU.likelion.GoldBalance.dto.response.PostInfo;
import KWU.likelion.GoldBalance.dto.response.PostList;
import KWU.likelion.GoldBalance.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController implements PostOperations {
    public final PostServiceImpl postService;

//    @Override //처음에 모든 정보를 받아 투표의 list를 반환함 - 댓글 순
//    public ResponseEntity<PostList> getPostsSortedByCommentCount() {
//        // 투표 순으로 정렬된 Post 받아오기
//        PostList postList = postService.getAllPostByMostComments();
//        // #####[수정]#######
//        // postService 에서 postList 객체로 반환 안하고 List<Post> 로 반환하는데 Service 에서 수정 필요해 보임!!
//        return new ResponseEntity<>(postList, HttpStatus.OK);
//    }

    @Override //처음에 모든 정보를 받아 투표의 list를 반환함 - 투표 순
    public ResponseEntity<PostList> getPostsSortedByVoteCount(@RequestParam Integer cursor) {
        PostList postList = postService.getAllPostByMostVotes(cursor); //커서 대로 인덱스 맞춰서 주기
        // #####[수정]#######
        // postService 에서 postList 객체로 반환 안하고 List<Post> 로 반환하는데 Service 에서 수정 필요해 보임!!
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @Override //처음에 모든 정보를 받아 투표의 list를 반환함 - 시간 순
    public ResponseEntity<PostList> getPostsSortedByTime(@RequestParam Integer cursor) {
        PostList postList = postService.getAllPostByNewest(cursor);//커서 대로 인덱스 맞춰서 주기
        // #####[수정]#######
        // postService 에서 postList 객체로 반환 안하고 List<Post> 로 반환하는데 Service 에서 수정 필요해 보임!!
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @Override // 5퍼 내로 차이나는 게시물을 불러올 수 있는 메서드
    public ResponseEntity<PostList> getPostsbyCloseVote(@RequestParam Integer cursor) {
        PostList postList = postService.getAllPostByCloseVote(cursor);//커서 대로 인덱스 맞춰서 주기
        // #####[수정]#######
        // postService 에서 postList 객체로 반환 안하고 List<Post> 로 반환하는데 Service 에서 수정 필요해 보임!!
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @Override //투표 게시물 저장
    public ResponseEntity<String> submitPost(MakePost makePost) {
        return null;
    }

    @Override //아이디에 대한 post 전달
    public ResponseEntity<PostInfo> getPostById(String id) {
        return null;
    }
}
