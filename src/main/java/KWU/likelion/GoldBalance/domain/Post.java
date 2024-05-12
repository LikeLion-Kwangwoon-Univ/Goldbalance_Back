package KWU.likelion.GoldBalance.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

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

    // post 생성자가 설정한 비밀번호
    private String password;

    @CreatedDate
    // post가 생성된 시각
    private LocalDateTime createdDateTime;

    // vs의 대상 중 왼쪽 개체의 제목
    private String leftSideTitle;

    // vs의 대상 중 왼쪽 개체의 설명
    private String leftSideDetail;

    // vs의 대상 중 왼쪽 개체의 이미지 경로
    private String leftSideImage;

    // vs의 대상 중 왼쪽 개체의 득표수
    private int leftSideVote;

    // vs의 대상 중 오른쪽 개체의 제목
    private String rightSideTitle;

    // vs의 대상 중 오른쪽 개체의 설명
    private String rightSideDetail;

    // vs의 대상 중 오른쪽 개체의 이미지 경로
    private String rightSideImage;

    // vs의 대상 중 오른쪽 개체의 득표수
    private int rightSideVote;

    // 댓글 연결 -> 아래에 구현된 코드는 댓글 대댓글 구분 없이 같은 postId면 다 불러옴...
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Comment> commentList;
}
