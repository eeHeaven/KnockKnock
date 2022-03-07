package jpaproject.knockknock.service.post_comment;

import com.amazonaws.services.s3.AmazonS3Client;
import jpaproject.knockknock.S3Uploader;
import jpaproject.knockknock.domain.post_comment.Image;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.repository.post_comment.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
public class ImageService extends S3Uploader {

    private ImageRepository imageRepository;

    public ImageService(AmazonS3Client amazonS3Client,ImageRepository imageRepository) {
        super(amazonS3Client);
        this.imageRepository = imageRepository;
    }

    @Transactional
    public String saveImage(MultipartFile multipartFile, String dirName, Post post) throws IOException {
        String uri = super.upload(multipartFile, dirName);
        Image img = new Image(uri,post);
        imageRepository.save(img);
        return uri;
    }
}
