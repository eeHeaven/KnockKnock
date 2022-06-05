package jpaproject.knockknock;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.HashTag;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.domain.post_comment.PostHashTag;
import jpaproject.knockknock.exception.CustomException;
import jpaproject.knockknock.exception.ExceptionEnum;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.post_comment.CommentRepository;
import jpaproject.knockknock.repository.post_comment.PostHashTagRepository;
import jpaproject.knockknock.api.request.CommentRequest;
import jpaproject.knockknock.api.request.PostSaveRequest;
import jpaproject.knockknock.repository.post_comment.HashTagRepository;
import jpaproject.knockknock.repository.post_comment.PostRepository;
import jpaproject.knockknock.service.post_comment.CommentService;
import jpaproject.knockknock.service.post_comment.PostService;
import org.assertj.core.api.Assertions;
import org.junit.Test;


import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostServiceTest {

   private final Logger logger = LoggerFactory.getLogger(PostServiceTest.class);

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @Autowired
    HashTagRepository hashTagRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostHashTagRepository postHashTagRepository;

    @Test
    public void 게시글작성()throws IndexOutOfBoundsException, IOException{
        //given
        Member writer = memberRepository.findByNickName("테스트멤버1").orElseThrow(()-> new IllegalArgumentException("해당 멤버 없음"));
        PostSaveRequest postSaveRequest = new PostSaveRequest("테스트","테스트용글입니다.",writer.getUserId(),"#test",1.1F,1.1F,"이대도서관");
        //when
        Post savedPost =  postService.save(postSaveRequest);
        //then
        Assertions.assertThat(savedPost.getPostwriter()).isEqualTo(writer);
    }
    @Test
    public void 게시글삭제()throws IOException {
        //given
        Member writer = memberRepository.findByNickName("테스트멤버1").orElseThrow(()->new IllegalArgumentException("해당 멤버 없음"));
        PostSaveRequest postSaveRequest = new PostSaveRequest("테스트","테스트용글입니다.",writer.getUserId(),"#test #인사",1.1F,1.1F,"이대도서관");
        List<String> tags = new ArrayList<>();
       Post savedPost =  postService.save(postSaveRequest);
        CommentRequest commentRequest = new CommentRequest(writer.getUserId(), savedPost.getId(), "this comment should be deleted");
        Comment targetComment = commentService.save(commentRequest);
        //when
        postService.delete(savedPost.getId());
        //then
        Post targetPost = postRepository.findById(savedPost.getId())
                .orElseThrow(()->new IllegalArgumentException("해당 게시글 없음"));
        HashTag targetHashTag = hashTagRepository.findByTag("게시글삭제")
                .orElseThrow(()-> new CustomException(ExceptionEnum.POST_NOT_FOUND));
        HashTag targetHashTag2 = hashTagRepository.findByTag("인사")
                .orElseThrow(()-> new IllegalArgumentException("해당 해시태그 없음"));
        List<PostHashTag> targetlist = postHashTagRepository.findByHashTag(targetHashTag2);
        Assertions.assertThat(targetPost).isNull();
        Assertions.assertThat(targetHashTag).isNull();
        Assertions.assertThat(targetHashTag2).isNotNull();
        Assertions.assertThat(targetlist.size()).isEqualTo(4);
        Comment comment = commentRepository.findById(targetComment.getId())
                .orElseThrow(()-> new CustomException(ExceptionEnum.COMMENT_NOT_FOUND));
        Assertions.assertThat(comment).isNull();
    }
}
