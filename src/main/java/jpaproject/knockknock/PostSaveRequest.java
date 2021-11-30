package jpaproject.knockknock;

import lombok.Data;

@Data
public class PostSaveRequest {
    private String title;
    private String content;

    private String hashTag;
}
