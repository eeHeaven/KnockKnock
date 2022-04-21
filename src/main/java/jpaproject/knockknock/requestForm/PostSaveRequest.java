package jpaproject.knockknock.requestForm;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostSaveRequest {
    private String title;
    private String content;
    private String writerId;
    private String hashTags;
    private Float lat;
    private Float lon;
    private String location;

    private PostSaveRequest(){}

    public PostSaveRequest(String title, String content, String writerId, String hashTags,Float lat,Float lon,String location) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.hashTags = hashTags;
        this.lat = lat;
        this.lon = lon;
        this.location = location;
    }


}
