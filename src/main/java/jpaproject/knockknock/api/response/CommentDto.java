package jpaproject.knockknock.api.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    Long commentId;
    String writerNickname;
    String writerId;
    String timestamp;
    String content;

    public CommentDto(Long commentId, String writerNickname,String writerId, LocalDateTime timestamp, String content) {
        this.commentId = commentId;
        this.writerNickname = writerNickname;
        this.writerId = writerId;
        this.timestamp = timestamp.toString();
        this.content = content;
    }
}
