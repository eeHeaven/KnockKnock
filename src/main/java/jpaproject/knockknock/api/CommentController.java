package jpaproject.knockknock.api;

import jpaproject.knockknock.api.request.CommentWriteRequest;
import jpaproject.knockknock.api.response.CommentDto;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.requestForm.CommentRequest;
import jpaproject.knockknock.service.post_comment.CommentService;
import jpaproject.knockknock.service.post_comment.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("api/{userId}/post/view/{postid}/comment")
    public CommentDto saveComment(@PathVariable("userId")String userid, @PathVariable("postid")Long postid, @RequestBody CommentWriteRequest request){
        CommentRequest commentRequest = new CommentRequest(userid,postid, request.getContent());
        Comment savedcomment = commentService.save(commentRequest);
        CommentDto response = new CommentDto(savedcomment.getId(), savedcomment.getCommentwriter().getNickName(),savedcomment.getCommentwriter().getUserId(),savedcomment.getTimestamp(),savedcomment.getContent());
        return response;
    }


}
