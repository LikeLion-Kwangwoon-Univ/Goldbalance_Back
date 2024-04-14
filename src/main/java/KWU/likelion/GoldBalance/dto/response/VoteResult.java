package KWU.likelion.GoldBalance.dto.response;

import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class VoteResult { // 사용자가 투표를 마쳤을 때 현재 투표의 결과를 반환
    // 왼쪽 요소의 득표수
    private int leftSideVote;
    
    // 오른쪽 요소의 득표수
    private int rightSideVote;    
}
