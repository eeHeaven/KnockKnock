package jpaproject.knockknock.domain.post_comment;

import jpaproject.knockknock.api.request.PostSaveRequest;
import jpaproject.knockknock.domain.Member;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;
    private Double latitude;
    private Double longitude;
    private String location;

    @OneToMany(mappedBy = "post")
    private List<PostHashTag> postTags = new ArrayList<PostHashTag>();

    @OneToMany(mappedBy = "post")
    private List<Comment> postcomments = new ArrayList<Comment>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member postwriter;

    @Column(name = "post_timedate")
    private String timestamp;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Image> img = new ArrayList<>();

    //생성 시간 timestamp 설정
    @PrePersist
    public void onPrePersist() {
        String s = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        this.timestamp = s;
    }

    //비즈니스 로직
    //연관관계 관련 로직
    public void setPostWriter(Member member) {
        this.postwriter = member;
        member.getPosts().add(this);
    }

    public void setImg(Image img) {
        this.img.add(img);
        img.setPost(this);
    }

    // 생성메서드
    //작성자, 이미지, 해시태그 새로 만들어야 함
    public static Post dtoToEntity(PostSaveRequest request) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .latitude((double) request.getLat())
                .longitude((double) request.getLon())
                .location(request.getLocation())
                .build();
    }


}
