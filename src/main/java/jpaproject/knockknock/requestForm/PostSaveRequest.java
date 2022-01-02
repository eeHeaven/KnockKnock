package jpaproject.knockknock.requestForm;

import lombok.Data;

@Data
public class PostSaveRequest {
    private String title;
    private String content;

    private String[] hashTags;
}
