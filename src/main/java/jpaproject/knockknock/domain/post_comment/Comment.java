package jpaproject.knockknock.domain.post_comment;

import jpaproject.knockknock.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
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
    private LocalDateTime timestamp;

    // 비즈니스 로직설계
    //1. 양방향 연관관계 관련 로직
    public void setCommentwriter(Member member){
        this.commentwriter = member;
        member.getMembercomments().add(this);
    }
    //생성메서드
    public Comment CreateComment(Member writer, Post post,String content){
        Comment comment = new Comment();
        comment.setTimestamp(LocalDateTime.now());
        comment.setPost(post);
        comment.setCommentwriter(writer);
        comment.setContent(content);
        return comment;
    }

}
