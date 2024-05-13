package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.dto.response.VoteResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class VoteController implements VoteOperations {
    @Override
    public ResponseEntity<String> vote(String id, String selectNum) {
        return null;
    }
    @Override
    public ResponseEntity<VoteResult> submitVoteResult(String id) {
        return null;
    }
}