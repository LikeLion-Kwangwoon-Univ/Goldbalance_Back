package KWU.likelion.GoldBalance.dto.request;

import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class MakeComment { // 새로운 댓글이 작성될 때 오는 request
    // 새로운 댓글이 작성된 post의 id
    private int postId;

    // 해당 댓글의 내용
    private String content;

    // 해당 댓글이 post에서 왼쪽인지 오른쪽인지 표시
    private int sideInfo;

    // 댓글이라면 -1, 대댓글이라면 댓글의 id
    private int parentCommentId;
}
