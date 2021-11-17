package jpaproject.knockknock.domain.post_comment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class HashTag {

    @Id @GeneratedValue
    @Column(name="tag_id")
    private Long id;

    private String tag;

    @OneToMany(mappedBy = "hashTag")
    private List<PostHashTag> postHashTags = new ArrayList<>();

    //생성메서드
    public HashTag createTag(String tag){
        HashTag hashTag = new HashTag();
        hashTag.setTag(tag);
        return hashTag;
    }
}
