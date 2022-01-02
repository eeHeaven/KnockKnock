package jpaproject.knockknock.domain.post_comment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class PostHashTag {

    @Id @GeneratedValue
    @Column(name="posttag_id")
    private Long id;

    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="tag_id")
    private HashTag hashtag;

    public void hashTagSet(HashTag hashTag){
        PostHashTag postHashTag = new PostHashTag();
        hashTag.getPosthashtags().add(this);
        this.setHashtag(hashTag);
    }
}
