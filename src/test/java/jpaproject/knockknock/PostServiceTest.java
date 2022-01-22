package jpaproject.knockknock;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.HashTag;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.domain.post_comment.PostHashTag;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.post_comment.CommentRepository;
import jpaproject.knockknock.requestForm.CommentRequest;
import jpaproject.knockknock.requestForm.PostSaveRequest;
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

    @Test
    public void 게시글작성(){
        //given
        PostSaveRequest postSaveRequest = new PostSaveRequest();
        postSaveRequest.setTitle("새 글입니다");
        postSaveRequest.setContent("반가워요");
        List<String> tags = new ArrayList<>();
        tags.add("인사");
        tags.add("소개");
        tags.add("새글");
        tags.add("테스트");
        Member writer = memberRepository.findByNickName("테스트멤버1");
        postSaveRequest.setWriterId(writer.getUserId());
        postSaveRequest.setHashTags(tags);
        //when
        Post savedPost =  postService.save(postSaveRequest);
        //then
        Assertions.assertThat(writer.getPosts().size()).isEqualTo(3);
        Assertions.assertThat(savedPost.getPostwriter()).isEqualTo(writer);
    }
    @Test
    public void 게시글삭제(){
        //given
        PostSaveRequest postSaveRequest = new PostSaveRequest();
        postSaveRequest.setTitle("새 글입니다");
        postSaveRequest.setContent("반가워요");
        Member member = memberRepository.findByNickName("테스트멤버1");
        postSaveRequest.setWriterId(member.getUserId());
        List<String> tags = new ArrayList<>();
        tags.add("인사");
        tags.add("게시글삭제");
        postSaveRequest.setHashTags(tags);
       Post savedPost =  postService.save(postSaveRequest);
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setComment("this comment should be deleted");
        commentRequest.setPostId(savedPost.getId());
        commentRequest.setWriterId(member.getId());
        Comment targetComment = commentService.save(commentRequest);
        //when
        postService.delete(savedPost.getId());
        //then
        Post targetPost = postRepository.findOneById(savedPost.getId());
        HashTag targetHashTag = hashTagRepository.findByTag("게시글삭제");
        HashTag targetHashTag2 = hashTagRepository.findByTag("인사");
        List<PostHashTag> targetlist = hashTagRepository.findPostHashTags(targetHashTag2.getId());
        Assertions.assertThat(targetPost).isNull();
        Assertions.assertThat(targetHashTag).isNull();     Assertions.assertThat(targetHashTag2).isNotNull();
        Assertions.assertThat(targetlist.size()).isEqualTo(4);
        Comment comment = commentRepository.findOne(targetComment.getId());
        Assertions.assertThat(comment).isNull();
    }
}
