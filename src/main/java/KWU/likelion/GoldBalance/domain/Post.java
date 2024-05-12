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
    @Column(name = "id")
    private int id;

    // post의 제목 (ex. 피자 vs 치킨)
    @Column(name = "postTitle")
    private String postTitle;

    @CreatedDate
    // post가 생성된 시각
    @Column(name = "createdDateTime")
    private LocalDateTime createdDateTime;

    // vs의 대상 중 왼쪽 개체의 제목
    @Column(name = "leftSideTitle")
    private String leftSideTitle;

    // vs의 대상 중 왼쪽 개체의 설명
    @Column(name = "leftSideDetail")
    private String leftSideDetail;

    // vs의 대상 중 왼쪽 개체의 득표수
    @Column(name = "leftSideVote")
    private int leftSideVote;

    // vs의 대상 중 오른쪽 개체의 제목
    @Column(name = "rightSideTitle")
    private String rightSideTitle;

    // vs의 대상 중 오른쪽 개체의 설명
    @Column(name = "rightSideDetail")
    private String rightSideDetail;

    // vs의 대상 중 오른쪽 개체의 득표수
    @Column(name = "rightSideVote")
    private int rightSideVote;

    // 댓글 연결 -> 아래에 구현된 코드는 댓글 대댓글 구분 없이 같은 postId면 다 불러옴...
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    private List<Comment> commentList;
}
