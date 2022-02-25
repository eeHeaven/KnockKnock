package jpaproject.knockknock.requestForm;

import lombok.Data;

@Data
public class CommentRequest {
    String writerId;
    Long postId;
    String Comment;

    public CommentRequest(String writerId, Long postId, String comment) {
        this.writerId = writerId;
        this.postId = postId;
        Comment = comment;
    }
}
