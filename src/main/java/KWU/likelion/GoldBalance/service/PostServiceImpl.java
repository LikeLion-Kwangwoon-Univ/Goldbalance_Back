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

    @Override
    public Post getPost(int postId) {
        return null;
    }

    @Override
    public List<Post> getAllPostByNewest() {
        return List.of();
    }

    @Override
    public List<Post> getAllPostByMostComments() {
        return List.of();
    }

    @Override
    public List<Post> getAllPostByMostVotes() {
        return List.of();
    }

    @Override
    public List<Post> getAllPostByCloseVote() {
        return List.of();
    }
}
