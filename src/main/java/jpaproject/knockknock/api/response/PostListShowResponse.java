package jpaproject.knockknock.api.response;

import jpaproject.knockknock.domain.post_comment.PostHashTag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostListShowResponse {

    Long id;
    String writerId;
    String title;
    String content;
    String postedTime;
    List<String> hashtag;

    public PostListShowResponse(Long id,String writerId, String title, String content, String postedTime, List<PostHashTag> hashtag) {
        this.id = id;
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.postedTime = postedTime;
        List<String> hashtaglist = new ArrayList<>();
        for(PostHashTag tag: hashtag){
            hashtaglist.add(tag.getTag());
        }
        this.hashtag = hashtaglist;
    }
}

