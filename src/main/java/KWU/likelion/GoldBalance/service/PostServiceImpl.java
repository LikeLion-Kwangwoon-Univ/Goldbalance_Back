package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    private VoteService voteService;

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getPost(int postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 ID: " + postId));
    }

    @Override
    public List<Post> getAllPostByNewest() {
        // 게시물을 생성일자(CreatedDateTime) 기준으로 내림차순 정렬하여 반환
        return postRepository.findAllByOrderByCreatedDateTimeDesc();
    }

    @Override
    public List<Post> getAllPostByMostComments() {
        return null;
    }

    @Override
    public List<Post> getAllPostByMostVotes() {
        // VoteService를 사용하여 각 게시물의 투표 수 계산
        // 투표 수가 가장 많은 게시물을 반환

        // 모든 게시물 조회
        List<Post> allPosts = postRepository.findAll();

        // 각 게시물의 좌우 투표 수를 더해 계산하고, 투표 수 기준으로 게시물 정렬
        // 투표 수가 가장 많은 게시물이 리스트의 첫 번째 요소로 반환
        allPosts.sort((post1, post2) -> {
            int post1Votes = voteService.getVoteResult(post1.getId()).getLeftSideVote() + voteService.getVoteResult(post1.getId()).getRightSideVote();
            int post2Votes = voteService.getVoteResult(post2.getId()).getLeftSideVote() + voteService.getVoteResult(post2.getId()).getRightSideVote();
            return post2Votes - post1Votes;
        });

        return allPosts;
    }

    @Override
    public List<Post> getAllPostByCloseVote() {
        // VoteService를 사용하여 각 게시물의 투표 비율을 계산
        // 투표 비율이 5% 이내로 차이가 나는 게시물을 반환
        return null;
    }
}
