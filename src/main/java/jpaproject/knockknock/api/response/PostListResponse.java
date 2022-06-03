package jpaproject.knockknock.api.response;

import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.domain.post_comment.PostHashTag;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostListResponse {

    private Long id;
    private String writerId;
    private String title;
    private String content;
    private String postedTime;
    private List<String> hashtag;

    public static PostListResponse entityToDto(Post post){
        return PostListResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .postedTime(post.getTimestamp())
                .title(post.getTitle())
                .writerId(post.getPostwriter().getUserId())
                .hashtag(post.getPostTags().stream().map(PostHashTag::getTag).collect(Collectors.toList()))
                .build();
    }
    public static List<PostListResponse> entityListToDtoList(List<Post> post){
        return post.stream()
                .map(p->PostListResponse.entityToDto(p))
                .collect(Collectors.toList());
    }
}

