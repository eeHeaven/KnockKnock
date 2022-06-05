package jpaproject.knockknock.domain.post_comment;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostHashTag {

    @Id
    @GeneratedValue
    @Column(name = "posttag_id")
    private Long id;

    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private HashTag hashtag;

    public void setHashtag(HashTag hashTag) {
        hashTag.getPosthashtags().add(this);
        this.hashtag = hashTag;
    }

    public void setPost(Post post) {
        this.post = post;
        post.getPostTags().add(this);
    }

    public PostHashTag(String tag) {
        this.tag = tag;
    }
}
