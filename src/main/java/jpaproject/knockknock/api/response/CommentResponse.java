package jpaproject.knockknock.api.response;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Comment;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CommentResponse {

    private Long commentId;
    private String writerNickname;
    private String writerId;
    private String timestamp;
    private String content;

    public static CommentResponse entityToDto(Comment comment){
        Member writer = comment.getCommentwriter();
        return builder()
                .commentId(comment.getId())
                .timestamp(comment.getTimestamp())
                .content(comment.getContent())
                .writerId(writer.getNickName())
                .writerNickname(writer.getUserId())
                .build();
    }

    public static List<CommentResponse> entityListToDtoList(List<Comment> comment){
        return comment.stream()
                .map(c -> CommentResponse.entityToDto(c))
                .collect(Collectors.toList());
    }
}
