package jpaproject.knockknock;

import jpaproject.knockknock.domain.post_comment.HashTag;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.domain.post_comment.PostHashTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostsRepository postsRepository;
    private final HashTagsRepository hashTagsRepository;
    private final PostHashTagRepository postHashTagRepository;

    public void save(PostSaveRequest postSaveRequest) {
        // 1. Post 저장
        Post post = new Post();
        post.setTitle(postSaveRequest.getTitle());
        post.setContent(postSaveRequest.getContent());
        Post savedPost = postsRepository.save(post);

        // 2. HashTag 존재하면 기존것을 사용, 없으면 새로 저장
        HashTag hashTag = hashTagsRepository.findByTag(postSaveRequest.getHashTag());
        if (hashTag == null) {
            HashTag newHashTag = new HashTag();
            newHashTag.setTag(postSaveRequest.getHashTag());

            hashTag = hashTagsRepository.save(newHashTag);
        }

        // 3. PostHashTag 저장
        PostHashTag postHashTag = new PostHashTag();
        postHashTag.setPost(savedPost);
        postHashTag.setHashTag(hashTag);
        postHashTagRepository.save(postHashTag);
    }
}
