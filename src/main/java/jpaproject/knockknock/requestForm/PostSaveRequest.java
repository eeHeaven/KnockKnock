package jpaproject.knockknock.requestForm;

import lombok.Data;

@Data
public class PostSaveRequest {
    private String title;
    private String content;
    private Long writerId;
    private String[] hashTags;
}
