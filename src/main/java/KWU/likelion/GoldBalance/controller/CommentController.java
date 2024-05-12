package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.domain.Comment;
import KWU.likelion.GoldBalance.domain.Post;
import KWU.likelion.GoldBalance.dto.request.Like;
import KWU.likelion.GoldBalance.dto.request.MakeComment;
import KWU.likelion.GoldBalance.dto.response.AddLikeCount;
import KWU.likelion.GoldBalance.dto.response.CommentList;
import KWU.likelion.GoldBalance.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class CommentController implements CommentOperations{

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //postId 로 모든 댓글 가져 오기 (10개씩?)
    @Override
    public ResponseEntity<CommentList> getCommentsByPostId(Long postId) {
        // 모든 댓글 가져오기
        List<Comment> allComments = commentService.getAllComment();

        // 해당 postId에 대한 부모 댓글 및 자식 댓글 분리
        List<Comment> parentComments = allComments.stream()
                .filter(comment -> Long.valueOf(comment.getPostId()).equals(postId) && comment.getParentCommentId() == -1)
                .toList();

        // 각 부모 댓글에 대한 자식 댓글 추가
        List<Comment> sortedComments = new ArrayList<>();
        for (Comment parentComment : parentComments) {
            sortedComments.add(parentComment);
//            List<Comment> childComments = getChildComments(allComments, parentComment.getId());
            List<Comment> childComments = commentService.getAllChildComment(parentComment.getId());
            sortedComments.addAll(childComments);
        }

        CommentList commentList = new CommentList();
        commentList.setCommentList(sortedComments);
        return ResponseEntity.ok(commentList);
    }

    // postId, selectSide 에 따른 모든 댓글 가져오기
    @Override
    public ResponseEntity<CommentList> getCommentsByPostIdAndSelectSide(Long postId, int selectSide) {
        // 모든 댓글 가져오기
        List<Comment> allComments = commentService.getAllComment();

//        // 해당 postId와 selectSide에 대한 부모 댓글 및 자식 댓글 분리
//        List<Comment> parentComments = allComments.stream()
//                .filter(comment -> Long.valueOf(comment.getPostId()).equals(postId) && comment.getSideInfo() == selectSide && comment.getParentCommentId() == -1)
//                .toList();
//
//        // 각 부모 댓글에 대한 자식 댓글 추가
//        List<Comment> sortedComments = new ArrayList<>();
//        for (Comment parentComment : parentComments) {
//            sortedComments.add(parentComment);
////            List<Comment> childComments = getChildComments(allComments, parentComment.getId());
//            List<Comment> childComments = commentService.getAllChildComment(parentComment.getId());
//            sortedComments.addAll(childComments);
//        }

        CommentList commentList = new CommentList();
//        commentList.setCommentList(sortedComments);
        commentList.setCommentList(allComments);
        return ResponseEntity.ok(commentList);

    }

    // postId, selectSide 에 따른 부모 댓글 가져오기
    @Override
    public ResponseEntity<CommentList> getParentCommentsByPostIdAndSelectSide(Long postId, int selectSide) {
        // 모든 댓글 가져오기
        List<Comment> allComments = commentService.getAllComment();

        // 해당 postId에 대한 부모 댓글
        List<Comment> parentComments = allComments.stream()
                .filter(comment -> Long.valueOf(comment.getPostId()).equals(postId) && comment.getParentCommentId() == -1)
                .toList();
        CommentList commentList = new CommentList();
        commentList.setCommentList(parentComments);
        return ResponseEntity.ok(commentList);
    }

    // postId, selectSide, commentId 에 따른 자식 댓글 가져오기
    @Override
    public ResponseEntity<CommentList> getChildCommentsPostIdAndSelectSide(Long postId, int selectSide,int commentId) {
        // 모든 댓글 가져오기
        List<Comment> allComments = commentService.getAllComment();

        // 해당 postId에 대한 부모 댓글
        List<Comment> childComments = allComments.stream()
                .filter(comment -> Long.valueOf(comment.getPostId()).equals(postId) && comment.getParentCommentId() == commentId && comment.getSideInfo() == selectSide && comment.getParentCommentId() == -1)
                .toList();

        CommentList commentList = new CommentList();
        commentList.setCommentList(childComments);
        return ResponseEntity.ok(commentList);
    }

    // 댓글 작성 (부모, 자식 댓글 작성) => 따로 만들기 , 오랜된 것부터 정렬해서
    @Override
    public ResponseEntity<CommentList> postComment(MakeComment makeCommentDto) {

        // 기존의 댓글 리스트를 가져옴
        List<Comment> postComments = commentService.getAllComment()
                .stream()
                .filter(comment ->
                        (comment.getPostId()==makeCommentDto.getPostId())
                                && comment.getSideInfo() == makeCommentDto.getSideInfo())
                //postId 가 같고 selectSide 가 같은 것 filtering
                .collect(Collectors.toList());

        CommentList commentList = new CommentList();
        commentList.setCommentList(postComments); // 기존의 댓글 리스트를 설정

        if (makeCommentDto.getParentCommentId() == -1) {
            // 부모 댓글 작성
            Comment comment = new Comment();
            comment.setPostId(makeCommentDto.getPostId());
            comment.setPassword(makeCommentDto.getPassword());
            comment.setContent(makeCommentDto.getContent());
            comment.setSideInfo(makeCommentDto.getSideInfo());
            comment.setParentCommentId(-1); // 부모 댓글의 경우 parentId는 -1

            Comment savedComment = commentService.createComment(comment);

            // 새로운 댓글을 기존의 CommentList에 추가합니다.
            commentList.getCommentList().add(savedComment);
        } else {
            // 대댓글 작성
            Comment parentComment = commentService.getComment(makeCommentDto.getParentCommentId());
            if (parentComment != null && (parentComment.getPostId())==makeCommentDto.getPostId()) {
                // 부모 댓글이 존재하고, 해당 부모 댓글이 요청된 게시물에 속한 경우에만 대댓글 작성
                Comment comment = new Comment();
                comment.setPostId(makeCommentDto.getPostId());
                comment.setPassword(makeCommentDto.getPassword());
                comment.setContent(makeCommentDto.getContent());
                comment.setSideInfo(makeCommentDto.getSideInfo());
                comment.setParentCommentId(makeCommentDto.getParentCommentId()); // 부모 댓글의 postId를 parentId로 설정

                Comment newComment = commentService.createComment(comment);

                // 새로운 댓글을 기존의 CommentList에 추가
                commentList.getCommentList().add(newComment);
            } else {
                // 부모 댓글이 없거나 요청된 게시물에 속하지 않는 경우 예외 처리
                return ResponseEntity.badRequest().build();
            }
        }

        // 새로운 댓글이 추가된 CommentList를 ResponseEntity로 반환
        return ResponseEntity.ok(commentList);
    }

    // postId, commentId 에 따른 좋아요 update
    @Override
    public ResponseEntity<AddLikeCount> postLikeCount(Like likeDto) {
        return null;
    }
}
