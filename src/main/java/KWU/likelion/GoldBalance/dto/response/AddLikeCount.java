package KWU.likelion.GoldBalance.dto.response;

import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class AddLikeCount { //좋아요를 눌렀을 때 반환
    // 좋아요를 누른 뒤 증가한 수치를 반영
    private int likeCount;
}
