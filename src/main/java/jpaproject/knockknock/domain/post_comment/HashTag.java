package jpaproject.knockknock.domain.post_comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Document(indexName = "hashtag")
@Getter @Setter
public class HashTag {

    @Id @GeneratedValue
    @Column(name="tag_id")
    private Long id;

    private String tag;

    @Transient
    @OneToMany(mappedBy = "hashtag")
    private List<PostHashTag> posthashtags = new ArrayList<>();

}
