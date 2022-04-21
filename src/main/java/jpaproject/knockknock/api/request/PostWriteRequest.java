package jpaproject.knockknock.api.request;

import lombok.Data;

@Data
public class PostWriteRequest {

    String title;
    String content;
    String hashtag;
    Float lat;
    Float lon;
    String location;
}
