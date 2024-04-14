package KWU.likelion.GoldBalance.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Entity
@NoArgsConstructor
public class Comment {  // comment table과 mapping될 도메인
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // commentId
    private int id;

    // comment가 위치해 있는 post의 id
    private int postId;

    // comment작성자가 설정한 비밀번호
    private String password;

    // comment내용
    private String content;
    
    @CreatedDate
    // comment가 달린 시각
    private LocalDateTime createdDateTime;
    
    // comment의 좋아요 수
    private int likeCount;
    
    // post에 속한 두 개의 오브젝트 중 어느 쪽인지 표시
    private int sideInfo;
    
    // 대댓글의 수
    private int childCount;
    
    // 현재 comment가 댓글이라면 -1, 대댓글이라면 댓글의 id
    private int parentCommentId;
}
