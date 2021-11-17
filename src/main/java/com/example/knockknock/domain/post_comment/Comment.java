package com.example.knockknock.domain.post_comment;

import com.example.knockknock.domain.Member;

import javax.persistence.*;

@Entity
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
}
