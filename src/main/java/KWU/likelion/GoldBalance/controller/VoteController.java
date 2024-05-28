package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.dto.response.VoteResult;
import KWU.likelion.GoldBalance.service.VoteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("goldbalance/select")
@RequiredArgsConstructor
public class VoteController implements VoteOperations {
    private final VoteServiceImpl voteService;
    @Override // 클라이언트가 한 투표를 받고 저장
    public ResponseEntity<String> vote(String id, String selectSide) {
        try {
            int idValue = Integer.parseInt(id);
            voteService.vote(idValue, selectSide);
        } catch (NumberFormatException e) {
            // id가 long으로 파싱할 수 없는 경우 예외 처리
            // 여기에 적절한 예외 처리 코드를 작성하세요
            System.out.println("잘못된 형식의 id입니다.");
            e.printStackTrace(); // 예외 정보를 출력하거나 로깅하는 등의 추가 처리를 할 수 있습니다.
        }
        return ResponseEntity.ok("투표 완료!");
    }
    @Override    //해당 포스트에 대한 투표 결과(VoteResult 객체에) 주기 ex> 왼쪽 5표 오른쪽 8표 이런식으로
    public ResponseEntity<VoteResult> getVoteResult(String id) {
        VoteResult voteResult = new VoteResult();
        try {
            voteResult = voteService.getVoteResult(Integer.parseInt(id));
        }
        catch (NumberFormatException e){
            System.out.println("잘못된 형식의 id입니다.");
            e.printStackTrace(); // 예외 정보를 출력하거나 로깅하는 등의 추가 처리를 할 수 있습니다.
        }

        return new ResponseEntity<>(voteResult, HttpStatus.OK); // VoteResult 전달
    }
}