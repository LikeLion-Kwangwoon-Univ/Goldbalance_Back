package KWU.likelion.GoldBalance.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Select {
    // postId
    private int id;

    // 0: left, 1: right
    private int selectNum;
}
