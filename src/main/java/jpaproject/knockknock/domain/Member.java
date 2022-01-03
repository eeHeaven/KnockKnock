package jpaproject.knockknock.domain;

import jpaproject.knockknock.domain.message.MessageDialog;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.domain.report.Report;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String nickName;
    private String userId;
    private String userPassword;

    private int sharePoint;

    @OneToMany(mappedBy = "postwriter")
    private List<Post> posts = new ArrayList<Post>();

    private boolean isBlocked;
    private int reportCount;

    @OneToMany(mappedBy = "member")
    private List<MessageDialog> messagesWithUser = new ArrayList<>();

    @OneToMany(mappedBy="commentwriter")
    private List<Comment> membercomments = new ArrayList<Comment>();

    //생성 메서드
    public Member createMember(String nickName, String userId, String userPassword){
        Member member = new Member();
        member.setNickName(nickName);
        member.setUserId(userId);
        member.setUserPassword(userPassword);
        member.setSharePoint(100);
        member.setBlocked(false);
        member.setReportCount(0);
        return member;
    }

    // 비즈니스 로직 - 단순연산 데이터관리
    public void sendMessage(){
        this.sharePoint = this.getSharePoint() - 10;
    }
    public void viewPost(){
        this.sharePoint = this.getSharePoint() - 5;
    }



}
