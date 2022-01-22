package jpaproject.knockknock.requestForm;

import lombok.Data;

import java.util.List;

@Data
public class PostSaveRequest {
    private String title;
    private String content;
    private String writerId;
    private List<String> hashTags;

    public PostSaveRequest(){}

    public PostSaveRequest(String title, String content, String writerId, List<String> hashTags) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.hashTags = hashTags;
    }
}
