package KWU.likelion.GoldBalance.service;

import KWU.likelion.GoldBalance.dto.response.VoteResult;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService{

    @Override
    public void vote(Long postId, String side) {

    }

    @Override
    public VoteResult getVoteResult(Long postId) {
        return null;
    }
}
