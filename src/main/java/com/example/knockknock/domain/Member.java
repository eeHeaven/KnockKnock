package com.example.knockknock.domain;

import com.example.knockknock.domain.message.UserMessage;
import com.example.knockknock.domain.post_comment.Comment;
import com.example.knockknock.domain.post_comment.Post;
import com.example.knockknock.domain.report.Report;
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

    @OneToMany(mappedBy = "objectMember")
    private List<Report> reports = new ArrayList<Report>();

    private boolean isBlocked;
    private int reportCount;

    @OneToMany(cascade = CascadeType.ALL)
    private List<UserMessage> messages = new ArrayList<UserMessage>();

    @OneToMany(mappedBy="commentwriter")
    private List<Comment> membercomments = new ArrayList<Comment>();

}
