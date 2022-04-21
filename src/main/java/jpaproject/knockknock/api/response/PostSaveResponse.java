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
    List<String> hashtag;
    List<String> img;
    String location;


    public PostSaveResponse(Long id, String writerId, String title, String content, String time,String location) {
        this.writerId = writerId;
        this.id = id;
        this.title = title;
        this.content = content;
        this.postedTime = time;
        this.location = location;
    }

    public void setHashtag(List<String> hashtag) {
        this.hashtag = hashtag;
    }
    public void setImage(List<String> img) {this.img = img;}
}
