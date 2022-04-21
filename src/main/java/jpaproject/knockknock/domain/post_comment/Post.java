package jpaproject.knockknock.domain.post_comment;

import jpaproject.knockknock.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id @GeneratedValue
    @Column(name="post_id")
    private Long id;

    private String title;
    private String content;
    private Double latitude;
    private Double longitude;
    private String location;

    @OneToMany(mappedBy = "post")
    private List<PostHashTag> postTags = new ArrayList<PostHashTag>();

    @OneToMany(mappedBy="post")
    private List<Comment> postcomments = new ArrayList<Comment>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member postwriter;

    @Column(name="post_timedate")
    private String timestamp;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Image> img = new ArrayList<>();

    //비즈니스 로직
    //1. 연관관계 관련 로직
    public void setPostWriter(Member member){
        this.postwriter = member;
        member.getPosts().add(this);
    }

    public void addPostHashTag(PostHashTag postHashTag){
        postHashTag.setPost(this);
        this.getPostTags().add(postHashTag);
    }
    public Post(){}
    // 생성메서드
    public Post(Member writer, String title, String content,double latitude,double longitude,String location){
        this.setPostWriter(writer);
        this.setTitle(title);
        this.setContent(content);
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
    }

    @PrePersist
    public void onPrePersist(){
        String s = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        this.timestamp = s;
    }
}
