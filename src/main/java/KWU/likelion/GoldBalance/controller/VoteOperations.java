package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.dto.response.VoteResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/default")
public interface VoteOperations {

    @GetMapping("/{id}/{selectSide}") // 투표 하기
    ResponseEntity<String> vote(@PathVariable String id, @PathVariable String selectSide);
    @GetMapping("/{id}")    //해당 포스트에 대한 투표 결과(VoteResult 객체에) 주기 ex> 왼쪽 5표 오른쪽 8표 이런식으로
    ResponseEntity<VoteResult> getVoteResult(@PathVariable String id);
}
