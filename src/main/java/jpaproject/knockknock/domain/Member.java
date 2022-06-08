package jpaproject.knockknock.domain;

import jpaproject.knockknock.api.request.SignUpRequest;
import jpaproject.knockknock.domain.message.UserChatRoom;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.Post;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(builderMethodName = "memberBuilder")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
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
    private List<UserChatRoom> chatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "commentwriter")
    private List<Comment> membercomments = new ArrayList<Comment>();

    public static Member dtoToEntity(SignUpRequest request) {
        return memberBuilder()
                .userId(request.getMemberId())
                .userPassword(request.getMemberPassword())
                .nickName(request.getNickname())
                .build();
    }

    public void setPoint(int point) {
        this.sharePoint = point;
    }

    public int reported() {
        return ++this.reportCount;
    }


    public void setBlocked(boolean blocked) {
        this.isBlocked = blocked;
    }
}
