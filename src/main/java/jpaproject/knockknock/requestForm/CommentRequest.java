package jpaproject.knockknock.requestForm;

import lombok.Data;

@Data
public class CommentRequest {

    Long postId;
    String Comment;

}
