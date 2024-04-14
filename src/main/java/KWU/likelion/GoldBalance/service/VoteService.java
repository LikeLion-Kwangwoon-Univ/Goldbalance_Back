package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.dto.response.VoteResult;

public interface VoteService {
    // 투표를 진행하는 메소드
    // 여기서 side는 "left" 또는 "right"가 될 수 있음
    void vote(Long postId, String side);

    // 투표 결과를 반환하는 메소드
    VoteResult getVoteResult(Long postId);
}
