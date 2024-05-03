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
        return null;
    }

    @Override
    public List<Post> getAllPostByMostComments() {
        return null;
    }

    @Override
    public List<Post> getAllPostByMostVotes() {
        // VoteService를 사용하여 각 게시물의 투표 수 계산
        // 투표 수가 가장 많은 게시물을 반환
        return null;
    }

    @Override
    public List<Post> getAllPostByCloseVote() {
        // VoteService를 사용하여 각 게시물의 투표 비율을 계산
        // 투표 비율이 5% 이내로 차이가 나는 게시물을 반환
        return null;
    }
}
