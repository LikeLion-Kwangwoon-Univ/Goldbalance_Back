package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.dto.response.VoteResult;
import KWU.likelion.GoldBalance.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Page<Post> getAllPostByNewest(int page, int size) {
        // PageRequest 객체를 생성. 이 객체는 페이지네이션과 정렬을 위한 정보를 담고 있음
        // 게시물을 생성일자(CreatedDateTime) 기준으로 내림차순 정렬하여 반환
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDateTime"));
        // PostRepository의 findAll 메소드를 호출하여 페이지네이션된 게시물을 조회
        return postRepository.findAll(pageRequest);
    }

    @Override
    public List<Post> getAllPostByMostVotes(int page, int size) {
        // VoteService를 사용하여 각 게시물의 투표 수 계산
        // 투표 수가 가장 많은 게시물을 반환
        // 모든 게시물을 조회한 후, 투표 수를 계산하고 정렬하여 반환하는 형식이므로 메모리 효율 저하

        // 모든 게시물 조회
        List<Post> allPosts = postRepository.findAll();

        // 각 게시물의 좌우 투표 수를 더해 계산하고, 투표 수 기준으로 게시물 정렬
        // 투표 수가 가장 많은 게시물이 리스트의 첫 번째 요소로 반환
        allPosts.sort((post1, post2) -> {
            int post1Votes = voteService.getVoteResult(post1.getId()).getLeftSideVote() + voteService.getVoteResult(post1.getId()).getRightSideVote();
            int post2Votes = voteService.getVoteResult(post2.getId()).getLeftSideVote() + voteService.getVoteResult(post2.getId()).getRightSideVote();
            return post2Votes - post1Votes;
        });

        // 페이징 처리
        int start = page * size;
        int end = Math.min(start + size, allPosts.size());
        if (start > end){
            return new ArrayList<>(); // 페이지 범위가 게시물 수를 벗어나는 경우 빈 리스트 반환
        }

        return allPosts.subList(start, end);
    }

    @Override
    public List<Post> getAllPostByCloseVote(int page, int size) {
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

        // 페이징 처리
        int start = page * size;
        int end = Math.min(start + size, closeVotePosts.size());
        if (start > end){
            return new ArrayList<>();
        }

        return closeVotePosts.subList(start, end);
    }
}
