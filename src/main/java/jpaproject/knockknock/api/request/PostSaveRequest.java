package jpaproject.knockknock.api.request;

import jpaproject.knockknock.domain.post_comment.Post;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostSaveRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String writerId;
    private String hashTags;
    @NotNull
    private Float lat;
    @NotNull
    private Float lon;
    @NotBlank
    private String location;

}
