package com.example.knockknock.domain.post_comment;

import com.example.knockknock.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "post")
    private List<PostHashTag> postTags = new ArrayList<PostHashTag>();

    @OneToMany(mappedBy="post")
    private List<Comment> postcomments = new ArrayList<Comment>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member postwriter;

}
