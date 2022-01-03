package jpaproject.knockknock.requestForm;

import lombok.Data;

@Data
public class CommentRequest {
    Long writerId;
    Long postId;
    String Comment;

}
