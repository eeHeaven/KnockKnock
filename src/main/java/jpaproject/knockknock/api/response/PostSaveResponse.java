package jpaproject.knockknock.api.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostSaveResponse {
    String writerId;
    String title;
    String content;
    LocalDateTime postedTime;

    public PostSaveResponse(String writerId, String title, String content,LocalDateTime time) {
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.postedTime = time;
    }
}
