package jpaproject.knockknock.domain.post_comment;

import jpaproject.knockknock.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {

    @Id @GeneratedValue
    @Column(name="post_id")
    private Long id;

    private String title;
    private String content;

    @OneToMany(mappedBy = "post" ,cascade = CascadeType.ALL)
    private List<PostHashTag> postTags = new ArrayList<PostHashTag>();

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL)
    private List<Comment> postcomments = new ArrayList<Comment>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member postwriter;

    @Column(name="post_timedate")
    private LocalDateTime timestamp;

    //비즈니스 로직
    //1. 연관관계 관련 로직
    public void setPostWriter(Member member){
        this.postwriter = member;
        member.getPosts().add(this);
    }
    public void addComment(Comment comment){
        this.getPostcomments().add(comment);
        comment.setPost(this);
    }
    public void addHashTag(String tag){
        PostHashTag postHashTag = new PostHashTag();
        postHashTag.setTag(tag);
        postHashTag.setPost(this);
        this.getPostTags().add(postHashTag);
    }
    // 생성메서드
    public static Post CreatePost(Member writer, String title, String content){
        Post post = new Post();
        LocalDateTime time = LocalDateTime.now();
        post.setTimestamp(time);
        post.setPostWriter(writer);
        post.setTitle(title);
        post.setContent(content);
        return post;
    }

}
