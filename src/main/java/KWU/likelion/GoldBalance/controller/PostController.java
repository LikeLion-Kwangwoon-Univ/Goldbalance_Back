package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.dto.request.MakePost;
import KWU.likelion.GoldBalance.dto.response.PostInfo;
import KWU.likelion.GoldBalance.dto.response.PostList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/")
public class PostController implements PostOperations {

    @Override
    public ResponseEntity<PostList> getPostsSortedByCommentCount() {
        return null;
    }

    @Override
    public ResponseEntity<PostList> getPostsSortedByVoteCount() {
        return null;
    }

    @Override
    public ResponseEntity<PostList> getPostsSortedByTime() {
        return null;
    }

    @Override //투표 게시물 저장
    public ResponseEntity<String> submitPost(MakePost makePost) {
        return null;
    }

    @Override //아이디데 대한 post 전달
    public ResponseEntity<PostInfo> getPostById(String id) {
        return null;
    }
}
