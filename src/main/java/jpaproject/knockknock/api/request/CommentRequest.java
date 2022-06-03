package jpaproject.knockknock.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentRequest {
    @NotBlank
    private String writerId;
    @NotNull
    private long postId;
    @NotBlank
    private String Comment;

}
