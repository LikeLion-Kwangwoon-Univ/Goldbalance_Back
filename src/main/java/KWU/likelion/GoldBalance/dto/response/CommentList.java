package KWU.likelion.GoldBalance.dto.response;

import KWU.likelion.GoldBalance.domain.Comment;

import java.util.List;

import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class CommentList {  // 현재 post에 대한 댓글 리스트를 반환
    // 댓글 덩어리들
    private List<Comment> commentList;
}
