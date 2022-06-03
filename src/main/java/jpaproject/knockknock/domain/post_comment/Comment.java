package jpaproject.knockknock.domain.post_comment;

import jpaproject.knockknock.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    @Column(name="comment_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member commentwriter;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    private String content;

    @Column(name="comment_timedate")
    private String timestamp;

    @PrePersist
    public void onPrePersist(){
        String s = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        this.timestamp = s;
    }

    //1. 양방향 연관관계 관련 로직
    private void setCommentwriter(Member member){
        this.commentwriter = member;
        member.getMembercomments().add(this);
    }

    private void addToPost(Post post){
        this.post = post;
        post.getPostcomments().add(this);
    }

    //생성메서드
    public static Comment create(Member writer, Post post,String content){
        Comment comment = new Comment();
        comment.addToPost(post);
        comment.setCommentwriter(writer);
        comment.content = content;
        return comment;
    }

}
