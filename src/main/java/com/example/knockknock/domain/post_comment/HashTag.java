package com.example.knockknock.domain.post_comment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class HashTag {

    @Id @GeneratedValue
    @Column(name="tag_id")
    private Long id;

    private String tag;

}
