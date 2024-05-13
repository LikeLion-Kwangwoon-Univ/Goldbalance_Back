package KWU.likelion.GoldBalance.dto.request;

import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class Like { // 좋아요를 누를 때마다 오는 request
    // 댓글이 속해있는 post의 id
    private int postId;
    
    // 호출당시 좋아요가 눌린 comment의 id
    private int commentId;

    // 갱신된 좋아요 수
    private int like;
}
