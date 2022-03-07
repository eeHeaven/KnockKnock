package jpaproject.knockknock.api.response;

import jpaproject.knockknock.domain.post_comment.PostHashTag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostSaveResponse {
    Long id;
    String writerId;
    String title;
    String content;
    String postedTime;
    String image;
    List<String> hashtag;
    List<String> img;


    public PostSaveResponse(Long id, String writerId, String title, String content, LocalDateTime time) {
        this.writerId = writerId;
        this.id = id;
        this.title = title;
        this.content = content;
        this.postedTime = time.toString();
    }

    public void setHashtag(List<String> hashtag) {
        this.hashtag = hashtag;
    }
    public void setImage(List<String> img) {this.img = img;}
}
