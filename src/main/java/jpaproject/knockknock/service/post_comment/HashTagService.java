package jpaproject.knockknock.service.post_comment;

import jpaproject.knockknock.domain.post_comment.HashTag;
import jpaproject.knockknock.repository.post_comment.HashTagRepository;
import jpaproject.knockknock.repository.post_comment.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashTagService {

    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;

    @Transactional
    public Long save(HashTag hashTag){
        hashTagRepository.save(hashTag);
        return hashTag.getId();
    }
}
