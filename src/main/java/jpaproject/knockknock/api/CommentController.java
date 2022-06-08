package jpaproject.knockknock.api;

import jpaproject.knockknock.api.response.CommentResponse;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.api.request.CommentRequest;
import jpaproject.knockknock.service.post_comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("api/post/view/comment")
    public CommentResponse saveComment(@RequestBody CommentRequest request) {
        Comment savedcomment = commentService.save(request);
        return CommentResponse.entityToDto(savedcomment);
    }


}
