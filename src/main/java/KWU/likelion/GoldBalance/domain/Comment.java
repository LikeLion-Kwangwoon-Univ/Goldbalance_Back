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
    @Column(name = "id")
    private int id;

    // comment가 위치해 있는 post의 id
    @Column(name = "postId")
    private int postId;

    // comment내용
    @Column(name = "content")
    private String content;
    
    @CreatedDate
    // comment가 달린 시각
    @Column(name = "createdDateTime")
    private LocalDateTime createdDateTime;
    
    // comment의 좋아요 수
    @Column(name = "likeCount")
    private int likeCount;
    
    // post에 속한 두 개의 오브젝트 중 어느 쪽인지 표시
    @Column(name = "sideInfo")
    private int sideInfo;
    
    // 대댓글의 수
    @Column(name = "childCount")
    private int childCount;
    
    // 현재 comment가 댓글이라면 -1, 대댓글이라면 댓글의 id
    @Column(name = "parentCommentId")
    private int parentCommentId;
}
