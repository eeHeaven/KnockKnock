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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final ImageService imageService;

    @Transactional
    public Post save(PostSaveRequest postSaveRequest) throws IOException {
        // 1. Post 저장
        Member writer = memberRepository.findByUserId(postSaveRequest.getWriterId());
        Post post = new Post(writer, postSaveRequest.getTitle(),postSaveRequest.getContent(),postSaveRequest.getLat(),postSaveRequest.getLon(),postSaveRequest.getLocation());
       Post savedPost =  postRepository.save(post);

        // 2. post에 추가한 posthashTag들 가져오기
        String hashTag = postSaveRequest.getHashTags();
        String[] hashTags = hashTag.split(" ");
        HashTag[] postHashTags = new HashTag[hashTags.length];
        int index = 0;
        for(String eachhashTag: hashTags){
            HashTag hashTageach = hashTagRepository.findByTag(eachhashTag);
            if (hashTageach == null) {
                HashTag newHashTag = new HashTag();
                newHashTag.setTag(eachhashTag);
                hashTageach = hashTagRepository.save(newHashTag);
            }
            postHashTags[index++] = hashTageach;
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

    //회원의 포스트 목록 가져오기
    public List<Post> getUserPosts(String userId){
       return postRepository.findByMember(userId);
    }

    //전체 포스트 목록 가져오기
    public List<Post> getPosts(){
        return postRepository.findAll();
    }

    public Post getPostById(Long id){return postRepository.findOneById(id);}

    //위치기반 근처 게시글 목록 가져오기
    public List<Post> getPostsbyLocation(double latitude, double longitude) {
        return postRepository.findByLocation(latitude,longitude);
    }
}
