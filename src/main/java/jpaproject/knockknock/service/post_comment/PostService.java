package jpaproject.knockknock.service.post_comment;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.PostHashTag;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.post_comment.CommentRepository;
import jpaproject.knockknock.requestForm.PostSaveRequest;
import jpaproject.knockknock.domain.post_comment.HashTag;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.repository.post_comment.HashTagRepository;
import jpaproject.knockknock.repository.post_comment.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Post save(PostSaveRequest postSaveRequest) {
        // 1. Post 저장
        Post post = new Post();
        post.setTitle(postSaveRequest.getTitle());
        post.setContent(postSaveRequest.getContent());
        post.setTimestamp(LocalDateTime.now());

        Member writer = memberRepository.findOne(postSaveRequest.getWriterId());
        post.setPostWriter(writer);
       Post savedPost =  postRepository.save(post);

        // 2. post에 추가한 posthashTag들 가져오기
        String[] hashTags = postSaveRequest.getHashTags();
        HashTag[] postHashTags = new HashTag[hashTags.length];
        int index = 0;
        for(String eachhashTag: hashTags){
            HashTag hashTag = hashTagRepository.findByTag(eachhashTag);
            if (hashTag == null) {
                HashTag newHashTag = new HashTag();
                newHashTag.setTag(eachhashTag);
                hashTag = hashTagRepository.save(newHashTag);
            }
            postHashTags[index++] = hashTag;
        }

        // 3. post에 hashtag 달기
        for(HashTag postHashTag: postHashTags) {
            PostHashTag realPostTag = new PostHashTag();
            savedPost.addPostHashTag(realPostTag);
            realPostTag.setTag(postHashTag.getTag());
            realPostTag.hashTagSet(postHashTag);
            PostHashTag savedPostTag = hashTagRepository.save(realPostTag);
        }
        
        return savedPost;
    }

    // 포스트 삭제
    @Transactional
    public void delete(Long id){
        Post post = postRepository.findOneById(id);
        // 해당 post에 있는 posthashTag 삭제, 연결된 유일한 hashtag도 삭제
        List<PostHashTag> postHashTags = post.getPostTags();
        for(PostHashTag eachPostTag: postHashTags ){
           if(eachPostTag.getHashtag().getPosthashtags().size()==1) hashTagRepository.removeHashTag(eachPostTag.getHashtag());
            hashTagRepository.removePostHashTag(eachPostTag);
        }
        // 해당 post에 있는 comments 삭제
        List<Comment> comments = post.getPostcomments();
        for(Comment eachComment : comments) commentRepository.remove(eachComment);

        // 포스트 삭제
        postRepository.remove(post);
    }




}