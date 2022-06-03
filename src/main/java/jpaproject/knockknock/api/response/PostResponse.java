package jpaproject.knockknock.api.response;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Post;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostResponse {

    private Long id;
    private String writernickname;
    private String writerId;
    private String title;
    private String content;
    private String postedTime;
    private String location;
    private List<CommentResponse> commentlist;
    private List<String> posthashtag;
    private String image;

   public static PostResponse entityToDto(Post post){
       Member writer = post.getPostwriter();
       return PostResponse.builder()
               .id(post.getId())
               .writernickname(writer.getNickName())
               .writerId(writer.getUserId())
               .title(post.getTitle())
               .content(post.getContent())
               .postedTime(post.getTimestamp())
               .location(post.getLocation())
               .image(post.getImg().get(0).getImgurl())
               .commentlist(CommentResponse.entityListToDtoList(post.getPostcomments()))
               .posthashtag(post.getPostTags().stream().map(t->t.getTag()).collect(Collectors.toList()))
               .build();
   }

}
