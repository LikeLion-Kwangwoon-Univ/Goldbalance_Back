package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.dto.request.MakePost;
import KWU.likelion.GoldBalance.dto.response.PostInfo;
import KWU.likelion.GoldBalance.dto.response.PostList;
import KWU.likelion.GoldBalance.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.Length;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<PostList> getPostsSortedByVoteCount(@RequestParam int cursor) {
        int size = 10;
        List<Post> postList = postService.getAllPostByMostVotes(cursor, size); //커서 대로 인덱스 맞춰서 주기
        PostList postList1 = new PostList();
        postList1.setPostList(postList);
        if (postList1.getPostList().size() % size != 0) postList1.setLastCursor(-1);//만약 마지막 페이지면? 필드에 -1 반환
        else postList1.setLastCursor(0);
        return new ResponseEntity<>(postList1, HttpStatus.OK);
    }

    @Override //처음에 모든 정보를 받아 투표의 list를 반환함 - 시간 순
    public ResponseEntity<PostList> getPostsSortedByTime(@RequestParam int cursor) {
        int size = 10;
        Page<Post> postPage = postService.getAllPostByNewest(cursor, size); //커서 대로 인덱스 맞춰서 주기
        PostList postList1 = new PostList();
        postList1.setPostList(postPage.getContent());
        if (postList1.getPostList().size() % size != 0) postList1.setLastCursor(-1);//만약 마지막 페이지면? 필드에 -1 반환
        else postList1.setLastCursor(0);
        return new ResponseEntity<>(postList1, HttpStatus.OK);
    }

    @Override // 5퍼 내로 차이나는 게시물을 불러올 수 있는 메서드
    public ResponseEntity<PostList> getPostsbyCloseVote(@RequestParam int cursor) {
        int size = 10;
        List<Post> postList = postService.getAllPostByCloseVote(cursor, size); //커서 대로 인덱스 맞춰서 주기
        PostList postList1 = new PostList();
        postList1.setPostList(postList);
        if (postList1.getPostList().size() % size != 0) postList1.setLastCursor(-1); //만약 마지막 페이지면? 필드에 -1 반환
        else postList1.setLastCursor(0);
        return new ResponseEntity<>(postList1, HttpStatus.OK);
    }

    @Override //투표 게시물 저장
    public ResponseEntity<String> submitPost(MakePost makePost) {

        Post post = new Post();
        post.setPostTitle(makePost.getLeftSideTitle() + " vs " + makePost.getRightSideTitle());
        post.setLeftSideTitle(makePost.getLeftSideTitle());
        post.setLeftSideDetail(makePost.getLeftSideDetail());
        post.setRightSideTitle(makePost.getRightSideTitle());
        post.setRightSideDetail(makePost.getRightSideDetail());
        post.setCreatedDateTime(LocalDateTime.now());
        postService.createPost(post);
        return ResponseEntity.ok("저장완료");
    }

    @Override //아이디에 대한 post 전달
    public ResponseEntity<PostInfo> getPostById(String id) {
        Post post = new Post();
        try {
            int idValue = Integer.parseInt(id);
            post = postService.getPost(idValue);
        } catch (NumberFormatException e) {
            // id가 int로 파싱할 수 없는 경우 예외 처리
            // 여기에 적절한 예외 처리 코드를 작성하세요
            System.out.println("잘못된 형식의 id입니다.");
            e.printStackTrace(); // 예외 정보를 출력하거나 로깅하는 등의 추가 처리를 할 수 있습니다.
        }
        PostInfo postInfo = new PostInfo();
        postInfo.setLeftSideTitle(post.getLeftSideTitle());
        postInfo.setRightSideTitle(post.getRightSideTitle());
        postInfo.setLeftSideDetail(post.getLeftSideDetail());
        postInfo.setRightSideDetail(post.getRightSideDetail());
        postInfo.setLeftSideVote(post.getLeftSideVote());
        postInfo.setRightSideVote(post.getRightSideVote());
        return new ResponseEntity<>(postInfo,HttpStatus.OK);
    }
}
