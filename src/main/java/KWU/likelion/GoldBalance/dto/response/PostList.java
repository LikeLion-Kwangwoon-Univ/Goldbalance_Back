package KWU.likelion.GoldBalance.dto.response;

import KWU.likelion.GoldBalance.domain.Post;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@NoArgsConstructor
public class PostList { // 사이트 접속 시 post들의 List를 반환
    // Post 덩어리
    private List<Post> PostList;
    private int lastCursor; // 클라이언트에게 마지막 페이진지 아닌지를 보내줌
}
