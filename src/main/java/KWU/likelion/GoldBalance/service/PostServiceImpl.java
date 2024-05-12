package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Override
    public Post createPost(Post post) {
        return null;
    }
    // #####[수정]#######
    // 컨트롤러에서 makePost 로 받아와서 넘겨주는데 service에서 객체를 Post로 받아옴 makePost 로 받아서 저장할 수 있게 service 에서 수정 있어야 할듯
    @Override
    public Post getPost(Long postId) {
        return null;
    }
    // #####[수정]#######
    // Post 로 반환이 아니라 PostInfo 로 반환해야함
    @Override
    public List<Post> getAllPostByNewest() {
        return null;
    }
    // #####[수정]#######
    // postService 에서 postList 객체로 반환 안하고 List<Post> 로 반환하는데 Service 에서 수정 필요해 보임!!
    @Override
    public List<Post> getAllPostByMostComments() {
        // 모든 게시물 조회
        List<Post> allPosts = postRepository.findAll();

        // 각 게시물의 댓글 수를 계산하고, 댓글 수 기준으로 게시물 정렬
        // 댓글이 가장 많은 게시물이 리스트의 첫 번째 요소로 반환
        allPosts.sort((post1, post2) -> post2.getCommentList().size() - post1.getCommentList().size());

        return allPosts;
    }
    // #####[수정]#######
    // postService 에서 postList 객체로 반환 안하고 List<Post> 로 반환하는데 Service 에서 수정 필요해 보임!!
    @Override
    public List<Post> getAllPostByMostVotes() {
        // VoteService를 사용하여 각 게시물의 투표 수 계산
        // 투표 수가 가장 많은 게시물을 반환
        return null;
    }
    // #####[수정]#######
    // postService 에서 postList 객체로 반환 안하고 List<Post> 로 반환하는데 Service 에서 수정 필요해 보임!!
    @Override
    public List<Post> getAllPostByCloseVote() {
        // VoteService를 사용하여 각 게시물의 투표 비율을 계산
        // 투표 비율이 5% 이내로 차이가 나는 게시물을 반환
        return null;
    } // #####[수정]#######
    // postService 에서 postList 객체로 반환 안하고 List<Post> 로 반환하는데 Service 에서 수정 필요해 보임!!
}
