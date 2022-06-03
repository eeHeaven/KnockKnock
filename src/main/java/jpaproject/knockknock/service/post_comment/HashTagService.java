package jpaproject.knockknock.service.post_comment;

import jpaproject.knockknock.domain.post_comment.HashTag;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.elk.HashTagESService;
import jpaproject.knockknock.repository.post_comment.HashTagRepository;
import jpaproject.knockknock.repository.post_comment.PostRepository;
import jpaproject.knockknock.repository.post_comment.PostRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashTagService {

    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;
    private final HashTagESService hashTagESService;
    private final PostRepositorySupport postRepositorySupport;

    @Transactional
    public HashTag save(HashTag hashTag){
        hashTagRepository.save(hashTag);
        hashTagESService.saveHashtag(hashTag);
        return hashTag;
    }
    @Transactional
    public void delete(HashTag hashTag){
        hashTagRepository.delete(hashTag);
        hashTagESService.deleteHashtag(hashTag);
    }

    public List<Post> PostListByTag(String hashtag){
        return postRepositorySupport.findByTag(hashtag);
    }
}
