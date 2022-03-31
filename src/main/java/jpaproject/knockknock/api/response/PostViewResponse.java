package jpaproject.knockknock.api.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostViewResponse {

    Long id;
    String writernickname;
    String writerId;
    String title;
    String content;
    String postedTime;
    String location;

    public void setImage(String image) {
        this.image = image;
    }

    List<CommentDto> commentlist;
    List<String> posthashtag;
    String image;

    public PostViewResponse(Long id, String writernickname,String writerId, String title, String content, String postedTime,String location) {
        this.id = id;
        this.writernickname = writernickname;
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.postedTime = postedTime;
        this.location = location;
    }

    public void setCommentlist(List<CommentDto> commentlist) {
        this.commentlist = commentlist;
    }
    public void setHashTagList(List<String> hashTagList){this.posthashtag = hashTagList;}
}
