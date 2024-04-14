package KWU.likelion.GoldBalance.dto.request;

import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class MakePost { // 밸런스 게임 생성할 때 오는 request
    // post의 제목
    private String postTitle;

    // post의 비밀번호
    private String password;

    // 왼쪽 요소의 제목
    private String leftSideTitle;

    // 왼쪽 요소의 설명
    private String leftSideDetail;

    // 오른쪽 요소의 제목
    private String rightSideTitle;

    // 오른쪽 요소의 설명
    private String rightSideDetail;
}

