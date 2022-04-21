package jpaproject.knockknock.api;

import jpaproject.knockknock.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PostImageController {

    private final S3Uploader s3Uploader;

    @PostMapping("/images")
    public String upload(@RequestParam("images")MultipartFile multipartFile) throws IOException{
        String url = s3Uploader.upload(multipartFile,"static");
        return url;
    }

}
