package KWU.likelion.GoldBalance.dto.response;

import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class PostInfo { // 특정 post를 눌러 투표화면으로 들어갈 때 post에 대한 정보를 반환
    // vs의 대상 중 왼쪽 개체의 제목
    private String leftSideTitle;

    // vs의 대상 중 왼쪽 개체의 설명
    private String leftSideDetail;

    // vs의 대상 중 왼쪽 개체의 득표수
    private int leftSideVote;

    // vs의 대상 중 오른쪽 개체의 제목
    private String rightSideTitle;

    // vs의 대상 중 오른쪽 개체의 설명
    private String rightSideDetail;

    // vs의 대상 중 오른쪽 개체의 득표수
    private int rightSideVote;
}
