package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.dto.response.VoteResult;
import KWU.likelion.GoldBalance.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService{

    private final PostRepository postRepository;

    @Autowired
    public VoteServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void vote(int postId, String side) {
        // Post 객체를 가져옴
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid postId: " + postId));

        // side 값에 따라 Post 객체의 leftSideVote 또는 rightSideVote 투표 수를 1 증가
        if ("left".equals(side)) {
            post.setLeftSideVote(post.getLeftSideVote() + 1);
        } else if ("right".equals(side)) {
            post.setRightSideVote(post.getRightSideVote() + 1);
        } else {
            throw new IllegalArgumentException("유효하지 않은 side: " + side);
        }

        // PostRepository의 save 메소드를 사용하여 변경된 Post 객체를 데이터베이스에 저장
        postRepository.save(post);
    }

    @Override
    public VoteResult getVoteResult(int postId) {
        // Post 객체를 가져옴
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않는 postId: " + postId));

        // VoteResult 객체를 생성하고 Post 객체의 leftSideVote와 rightSideVote 값을 설정
        VoteResult voteResult = new VoteResult();
        voteResult.setLeftSideVote(post.getLeftSideVote());
        voteResult.setRightSideVote(post.getRightSideVote());

        // VoteResult 객체를 반환
        return voteResult;
    }
}
