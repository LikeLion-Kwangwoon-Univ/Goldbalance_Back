package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.dto.response.PostList;
import KWU.likelion.GoldBalance.dto.response.AllPostList;
import KWU.likelion.GoldBalance.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("goldbalance")
public class HomeController implements HomeOperations{
    public final PostServiceImpl postService;
    @Override
    public ResponseEntity<Map<String, List<AllPostList>>> getHomeInfo() {
        List<AllPostList> allPostListList = new ArrayList<>();
        AllPostList allPostList1 = new AllPostList();
        List<Post> postsClose = postService.getAllPostByCloseVote(0, 4); //박빙
        allPostList1.setSubject("박빙 balance");
        allPostList1.setPostList(postsClose);
        allPostListList.add(allPostList1);
        AllPostList allPostList2 = new AllPostList();
        List<Post> postsHot = postService.getAllPostByMostVotes(0, 4); //인기
        allPostList2.setSubject("인기 balance");
        allPostList2.setPostList(postsHot);
        allPostListList.add(allPostList2);
        AllPostList allPostList3 = new AllPostList();
        Page<Post> postPage = postService.getAllPostByNewest(0, 1); //최근
        allPostList3.setSubject("최근 balance");
        allPostList3.setPostList(postPage.getContent());
        allPostListList.add(allPostList3);
        Map<String, List<AllPostList>> responseData = new HashMap<>();
        responseData.put("allPostList", allPostListList); //합쳐서 보내주기
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
