package jpaproject.knockknock;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.post_comment.CommentRepository;
import jpaproject.knockknock.repository.post_comment.PostRepository;
import jpaproject.knockknock.requestForm.CommentRequest;
import jpaproject.knockknock.requestForm.PostSaveRequest;
import jpaproject.knockknock.service.MemberService;
import jpaproject.knockknock.service.post_comment.CommentService;
import jpaproject.knockknock.service.post_comment.PostService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MemberService memberService;

    @Before
    public void 초기데이터설정(){
        PostSaveRequest postSaveRequest = new PostSaveRequest();
        postSaveRequest.setContent("테스트용 게시글");
        postSaveRequest.setHashTags("테스트");
        postSaveRequest.setTitle("테스트 중");

        Member member = new Member("테스트멤버1","testmember1","1234");
        memberService.signUp(member);

    }
    @Test
    public void 댓글달기(){
        //given
        List<Post> posts = postRepository.findByTag("테스트");
        Post post = posts.get(0);
        Member member = memberRepository.findByNickName("테스트멤버1");
        CommentRequest commentRequest = new CommentRequest(member.getUserId(),post.getId(),"nice to meet you");

        //when
        commentService.save(commentRequest);
        //then
        List<Comment> comments = post.getPostcomments();
        Comment comment = comments.get(1);
        Assertions.assertThat(comment.getContent()).isEqualTo("nice to meet you");
        Assertions.assertThat(comments.size()).isEqualTo(2);
        Assertions.assertThat(comment.getCommentwriter()).isEqualTo(member);

    }

    @Test
    public void 댓글삭제(){
        //given
        List<Post> posts = postRepository.findByTag("영어");
        Member member = memberRepository.findByNickName("테스트멤버1");
        Post post = posts.get(0);
        CommentRequest commentRequest = new CommentRequest(member.getUserId(),post.getId(),"this comment should be deleted");
        Comment savedComment= commentService.save(commentRequest);
        Long id = savedComment.getId();
        Comment target1 = commentRepository.findOne(savedComment.getId());
        //when
        commentService.delete(savedComment.getId());
        //then
        Comment target2 = commentRepository.findOne(savedComment.getId());
        Assertions.assertThat(target2).isNull();
        List<Post> posts2 = postRepository.findByTag("영어");
        Post post2 = posts2.get(0);
        List<Comment> comments = post2.getPostcomments();
        Assertions.assertThat(comments.size()).isEqualTo(0);
    }
}
