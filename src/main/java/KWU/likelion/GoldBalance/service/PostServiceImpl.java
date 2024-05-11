package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.dto.response.VoteResult;
import KWU.likelion.GoldBalance.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        // 모든 게시물 조회
        List<Post> allPosts = postRepository.findAll();

        // 각 게시물의 댓글 수를 계산하고, 댓글 수 기준으로 게시물 정렬
        // 댓글이 가장 많은 게시물이 리스트의 첫 번째 요소로 반환
        allPosts.sort((post1, post2) -> post2.getCommentList().size() - post1.getCommentList().size());

        return allPosts;
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

        // 모든 게시물 조회
        List<Post> allPosts = postRepository.findAll();

        // 각 게시물의 좌우 투표 비율을 계산하고, 투표 비율 차이가 5% 이내인 게시물만 필터링
        List<Post> closeVotePosts = allPosts.stream()
                .filter(post -> {
                    VoteResult voteResult = voteService.getVoteResult(post.getId());
                    int totalVotes = voteResult.getLeftSideVote() + voteResult.getRightSideVote();
                    if (totalVotes == 0) {
                        return false; // 투표가 없는 게시물은 제외
                    }
                    double leftVoteRatio = (double) voteResult.getLeftSideVote() / totalVotes; // 좌측 투표 비율
                    double rightVoteRatio = (double) voteResult.getRightSideVote() / totalVotes; // 우측 투표 비율
                    return Math.abs(leftVoteRatio - rightVoteRatio) <= 0.05; // 투표 비율 차이가 5% 이내인 경우만 포함
                })
                .collect(Collectors.toList()); // 필터링된 게시물을 리스트로 변환

        return closeVotePosts;
    }
}
