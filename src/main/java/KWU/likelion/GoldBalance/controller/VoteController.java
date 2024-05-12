package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.dto.response.VoteResult;
import KWU.likelion.GoldBalance.service.VoteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class VoteController implements VoteOperations {
    private final VoteServiceImpl voteService;
    @Override // 클라이언트가 한 투표를 받고 저장
    public ResponseEntity<String> vote(String id, String selectNum) {
        voteService.vote((long) Integer.parseInt(id), selectNum); // 일단 id를 long으로 형 변환해서 넘기겠음
        return ResponseEntity.ok("투표 완료!");
    }
    @Override    //해당 포스트에 대한 투표 결과(VoteResult 객체에) 주기 ex> 왼쪽 5표 오른쪽 8표 이런식으로
    public ResponseEntity<VoteResult> getVoteResult(String id) {
        VoteResult voteResult = voteService.getVoteResult(Long.parseLong(id)); // 일단 id 를 long으로 바꿔서 인자 전달
        return new ResponseEntity<>(voteResult, HttpStatus.OK); // VoteResult 전달
    }
}
