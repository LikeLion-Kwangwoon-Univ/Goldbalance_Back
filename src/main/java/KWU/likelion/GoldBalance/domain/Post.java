package KWU.likelion.GoldBalance.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Entity
@NoArgsConstructor
public class Post { // post table과 mapping될 도메인
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // post의 id
    private int id;

    // post의 제목 (ex. 피자 vs 치킨)
    private String postTitle;

    // vs의 대상 중 왼쪽 개체의 제목
    private String leftSideTitle;

    // vs의 대상 중 왼쪽 개체의 설명
    private String leftSideDetail;

    // vs의 대상 중 왼쪽 개체의 이미지 경로
    private String leftSideImage;

    // vs의 대상 중 왼쪽 개체의 득표수
    private String leftSideVote;

    // vs의 대상 중 오른쪽 개체의 제목
    private String rightSideTitle;

    // vs의 대상 중 오른쪽 개체의 설명
    private String rightSideDetail;

    // vs의 대상 중 오른쪽 개체의 이미지 경로
    private String rightSideImage;

    // vs의 대상 중 오른쪽 개체의 득표수
    private String rightSideVote;
}
