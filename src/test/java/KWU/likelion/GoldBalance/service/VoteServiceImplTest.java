package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.dto.response.VoteResult;
import KWU.likelion.GoldBalance.repository.PostRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class VoteServiceImplTest {

    @Test
    public void VoteTest() {
        // given
        // Post 객체를 생성하고 초기 투표 수를 설정
        Post post = new Post();
        post.setLeftSideVote(0);
        post.setRightSideVote(0);

        // PostRepository를 모킹하고, findById 메소드가 호출되면 위에서 생성한 Post 객체를 반환하도록 설정
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.findById(anyInt())).thenReturn(Optional.of(post));

        // VoteServiceImpl 객체를 생성하고, 모킹한 PostRepository를 주입
        VoteServiceImpl voteService = new VoteServiceImpl(postRepository);

        // when
        // vote 메소드를 호출하여 왼쪽 투표를 진행
        voteService.vote(1, "left");

        // then
        // 왼쪽 투표 수가 1 증가했는지 확인
        assertEquals(1, post.getLeftSideVote());
        // PostRepository의 save 메소드가 한 번 호출되었는지 확인
        verify(postRepository, times(1)).save(post);
    }

    @Test
    public void getVoteResultTest() {
        // given
        // Post 객체를 생성하고 초기 투표 수를 설정
        Post post = new Post();
        post.setLeftSideVote(10);
        post.setRightSideVote(20);

        // PostRepository를 모킹하고, findById 메소드가 호출되면 위에서 생성한 Post 객체를 반환하도록 설정
        PostRepository postRepository = mock(PostRepository.class);
        when(postRepository.findById(anyInt())).thenReturn(Optional.of(post));

        // VoteServiceImpl 객체를 생성하고, 모킹한 PostRepository를 주입
        VoteServiceImpl voteService = new VoteServiceImpl(postRepository);

        // when
        // getVoteResult 메소드를 호출하고 반환값을 저장
        VoteResult voteResult = voteService.getVoteResult(1);

        // then
        // 반환된 VoteResult 객체의 leftSideVote와 rightSideVote 값이 Post 객체의 값과 일치하는지 확인
        assertEquals(10, voteResult.getLeftSideVote());
        assertEquals(20, voteResult.getRightSideVote());
    }
}