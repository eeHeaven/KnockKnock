package jpaproject.knockknock.domain.post_comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String imgurl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    void setPost(Post post) {
        this.post = post;
    }

    public Image(String imgurl, Post post) {
        this.imgurl = imgurl;
        this.post = post;
        post.getImg().add(this);
    }

}
