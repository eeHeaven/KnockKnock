package jpaproject.knockknock.domain.post_comment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Image {
    @Id
    @GeneratedValue
    @Column(name="image_id")
    private Long id;

    String imgurl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Image(String imgurl, Post post) {
        this.imgurl = imgurl;
        this.post = post;
        post.getImg().add(this);
    }

    public Image() {

    }
}
