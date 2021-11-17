package jpaproject.knockknock.domain.post_comment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="tag_id")
    private HashTag hashTag;

    //비즈니스 로직
    //1.연관관계 관련 로직

    public void setHashTag(HashTag hashTag){
        this.hashTag = hashTag;
    }

    //생성메서드
    public PostHashTag CreatePostHashTag(String tag, HashTag hashTag){
        PostHashTag postHashTag = new PostHashTag();
        postHashTag.setTag(tag);
        postHashTag.setHashTag(hashTag);
        return postHashTag;
    }

}
