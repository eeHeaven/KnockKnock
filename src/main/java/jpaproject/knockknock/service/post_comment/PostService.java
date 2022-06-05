package jpaproject.knockknock.service.post_comment;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.PostHashTag;
import jpaproject.knockknock.exception.CustomException;
import jpaproject.knockknock.exception.ExceptionEnum;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.post_comment.*;
import jpaproject.knockknock.api.request.PostSaveRequest;
import jpaproject.knockknock.domain.post_comment.HashTag;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.strategy.factory.PointModifyFactory;
import jpaproject.knockknock.strategy.pointmodify.PointModify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PointModifyFactory pointModifyFactory;
    private final PostRepository postRepository;
    private final PostRepositorySupport postRepositorySupport;
    private final HashTagRepository hashTagRepository;
    private final HashTagService hashTagService;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ImageService imageService;
    private final PostHashTagRepository postHashTagRepository;

    @Transactional
    public Post save(PostSaveRequest postSaveRequest)throws IOException{
        // 1.작성자 set
        Member writer = memberRepository.findByUserId(postSaveRequest.getWriterId())
                .orElseThrow(()->new CustomException(ExceptionEnum.USER_NOT_FOUND));
        Post post = Post.dtoToEntity(postSaveRequest);
        post.setPostWriter(writer);

        // 2. posthashtag set
        String hashTag = postSaveRequest.getHashTags();
        String[] postHashTags = hashTag.split(" ");
        setPostHashTagToPost(postHashTags,post);

        //3. 포인트 변화
        PointModify pointModify = pointModifyFactory.findPointModify(PointModify.Situation.writePost);
        pointModify.modifyPointof(writer);

        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    //postHashTagArray에서 posthashtag를 생성 후 post에 연결
    private void setPostHashTagToPost(String[] postHashTagArray, Post post){
       for(String postHashTag : postHashTagArray){
           PostHashTag tag = postHashTagRepository.save(new PostHashTag(postHashTag));
           tag.setPost(post);
           setHashTagToPostHashTag(tag);
       }
    }
    //해당 posthashtag에 해당하는 hashtag가 있으면 해당 hashtag와 연결
    //없으면 새로 hashtag를 만든 후 연결
    private void setHashTagToPostHashTag(PostHashTag postHashTag){
        String tag = postHashTag.getTag();
        HashTag hashTag = hashTagRepository.findByTag(tag)
                .orElse(hashTagRepository.save(new HashTag(tag)));
        postHashTag.setHashtag(hashTag);
    }

    // 포스트 삭제
    @Transactional
    public void delete(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new CustomException(ExceptionEnum.POST_NOT_FOUND));
        // 해당 post에 있는 posthashTag 삭제, 연결된 유일한 hashtag도 삭제
        List<PostHashTag> postHashTags = post.getPostTags();
        for(PostHashTag eachPostTag: postHashTags ){
            //TODO: 이부분 query 나가는거 괜찮은지 확인해보기
           if(eachPostTag.getHashtag().getPosthashtags().size()==1) hashTagService.delete(eachPostTag.getHashtag());
            postHashTagRepository.delete(eachPostTag);
        }
        // 해당 post에 있는 comments 삭제
        List<Comment> comments = post.getPostcomments();
        for(Comment eachComment : comments) commentRepository.delete(eachComment);
        // 포스트 삭제
        postRepository.delete(post);
        //포인트 변화
        PointModify pointModify = pointModifyFactory.findPointModify(PointModify.Situation.deletePost);
        pointModify.modifyPointof(post.getPostwriter());
    }

    //회원의 포스트 목록 가져오기
    public List<Post> getUserPosts(String userId){
       Member member = memberRepository.findByUserId(userId).orElseThrow(()->new CustomException(ExceptionEnum.USER_NOT_FOUND));
        return postRepository.findByMember(member);
    }

    //전체 포스트 목록 가져오기
    public List<Post> getPosts(){
        return postRepository.findAll();
    }

    public Post getPostById(Long id){
        return postRepository.findById(id)
                .orElseThrow(()->new CustomException(ExceptionEnum.POST_NOT_FOUND));
                }

    //위치기반 근처 게시글 목록 가져오기
    public List<Post> getPostsbyLocation(double latitude, double longitude) {
        return postRepositorySupport.findByLocation(latitude,longitude);
    }
}
