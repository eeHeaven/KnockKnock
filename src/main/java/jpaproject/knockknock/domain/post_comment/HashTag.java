package jpaproject.knockknock.domain.post_comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Document(indexName = "hashtag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HashTag {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String tag;

    @Transient
    @OneToMany(mappedBy = "hashtag")
    private List<PostHashTag> posthashtags = new ArrayList<>();

    public HashTag(String tag) {
        this.tag = tag;
    }

}
