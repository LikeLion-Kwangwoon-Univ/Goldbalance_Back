package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.domain.Comment;
import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.dto.request.Like;
import KWU.likelion.GoldBalance.dto.request.MakeComment;
import KWU.likelion.GoldBalance.dto.response.CommentList;
import KWU.likelion.GoldBalance.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/goldbalance")
public class CommentController implements CommentOperations{

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // postId 에 따른 부모 댓글 가져오기
    @Override
    public ResponseEntity<CommentList> getParentCommentsByPostId( Long postId) {
        try {
            // 모든 댓글 가져오기
            List<Comment> allComments = commentService.getAllComment(postId.intValue());

            // 해당 postId에 대한 부모 댓글
            List<Comment> parentComments = new ArrayList<>(allComments.stream()
                    .filter(comment ->
                            comment.getParentCommentId() == -1)
                    .sorted(Comparator.comparing(Comment::getCreatedDateTime))
                    .toList());


            CommentList commentList = new CommentList();
            commentList.setCommentList(parentComments);
            return ResponseEntity.ok(commentList);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    // postId, commentId 에 따른 자식 댓글 가져오기
    @Override
    public ResponseEntity<CommentList> getChildCommentsPostIdAndCommentId( Long postId, Long commentId) {
        try {
            // commentId(부모commentId)의 모든 자식 댓글 가져오기
            List<Comment> allChildComment = commentService.getAllChildComment(commentId.intValue());

            // 해당 postId에 대한 자식 댓글
            List<Comment> childComments = allChildComment.stream()
                    .filter(comment -> comment.getPostId()==(postId)
                            && comment.getParentCommentId() != -1)
                    .sorted(Comparator.comparing(Comment::getCreatedDateTime))
                    .toList();

            CommentList commentList = new CommentList();
            commentList.setCommentList(childComments);
            return ResponseEntity.ok(commentList);
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 부모 댓글 작성, 오랜된 것부터 정렬해서
    @Override
    public ResponseEntity<CommentList> postComment( MakeComment makeCommentDto, Long postId) {
        try {
            //makeCommentDto.getParentCommentId() == -1 check
            if (makeCommentDto.getParentCommentId() == -1) { //부모 댓글에 대한 request가 맞는 지 check
                // 해당 postId의 기존 댓글 리스트를 가져옴
                List<Comment> postComments = commentService.getAllComment(postId.intValue());
//                        .stream()
//                        .filter(comment ->
//                                comment.getPostId() == makeCommentDto.getPostId())
//                        .collect(Collectors.toList());

                CommentList commentList = new CommentList();
//                commentList.setCommentList(postComments); // 기존의 댓글 리스트를 설정

                // 부모 댓글 작성
                Comment comment = new Comment();
                comment.setPostId(postId.intValue());
                comment.setContent(makeCommentDto.getContent());
                comment.setCreatedDateTime(LocalDateTime.now());
                comment.setChildCount(0);
                comment.setSideInfo(makeCommentDto.getSideInfo());
                comment.setParentCommentId(-1); // 부모 댓글의 경우 parentId는 -1

                Comment savedComment = commentService.createComment(comment);
                postComments.add(savedComment);

                // commentList를 createdTime을 기준으로 오름차순으로 정렬
                postComments.sort(Comparator.comparing(Comment::getCreatedDateTime));
                // 새로운 댓글을 기존의 CommentList에 추가합니다.
                commentList.setCommentList(postComments); // 기존의 댓글 리스트를 설정


                // 새로운 댓글이 추가된 CommentList를 ResponseEntity로 반환
                return ResponseEntity.ok(commentList);
            } else {
                // 부모 댓글이 없거나 요청된 게시물에 속하지 않는 경우 예외 처리
                return ResponseEntity.badRequest().build();
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    // 대댓글 작성
    @Override
    public ResponseEntity<CommentList> postRecomment( MakeComment makeCommentDto,  Long postId) {
        try {
            Comment parentComment = commentService.getComment(makeCommentDto.getParentCommentId());
            // 부모 댓글이 존재하고, 해당 부모 댓글이 요청된 게시물에 속한 경우에만 대댓글 작성
            if (makeCommentDto.getParentCommentId() != -1
                    && parentComment != null
                    && parentComment.getPostId() == postId) { //부모 댓글이 아닌 경우

                // 해당 postId의 기존 대댓글 리스트를 가져옴
                List<Comment> postComments = commentService.getAllChildComment(makeCommentDto.getParentCommentId())
                        .stream()
                        .filter(comment ->
                                comment.getPostId() == postId
                                        && comment.getSideInfo() == makeCommentDto.getSideInfo()
                        )
                        .collect(Collectors.toList());

                //대댓글이 추가되면 childCount + 1 => service 구현 필요!!!
                parentComment.setChildCount(parentComment.getChildCount()+1);

                CommentList commentList = new CommentList();
                Comment comment = new Comment();
                comment.setPostId(postId.intValue());
                comment.setCreatedDateTime(LocalDateTime.now());
                comment.setContent(makeCommentDto.getContent());
                comment.setSideInfo(makeCommentDto.getSideInfo());
                comment.setParentCommentId(makeCommentDto.getParentCommentId());

                Comment savedComment = commentService.createComment(comment);
                postComments.add(savedComment);

                // commentList를 createdTime을 기준으로 오름차순으로 정렬
                postComments.sort(Comparator.comparing(Comment::getCreatedDateTime));

                commentList.setCommentList(postComments); // 기존의 댓글 리스트를 설정
                return ResponseEntity.ok(commentList);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    // postId, commentId 에 따른 좋아요 update(추가, 삭제)
    @Override
    public ResponseEntity<String> postLikeCount( Like likeDto, Long postId, Long commentId, Long like) {
        try {
            if (like == 1) {//like 증가
                commentService.likeComment(commentId.intValue());
                return ResponseEntity.ok("좋아요 증가에 성공했습니다.");
            } else if (like == 0) {//like 감소
                // sevice 메서드 추가 필요
                return ResponseEntity.ok("좋아요 감소에 성공했습니다.");

            } else {//유효하지 않은 값
                return ResponseEntity.badRequest().body("좋아요 업데이트에 실패했습니다.");
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
