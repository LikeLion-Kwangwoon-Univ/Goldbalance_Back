package KWU.likelion.GoldBalance.dto.response;

import KWU.likelion.GoldBalance.domain.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AllPostList {
    private List<Post> PostList; //포스트 리스트
    private String subject; //파트의 제목

}
