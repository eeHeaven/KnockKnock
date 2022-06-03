package jpaproject.knockknock;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.post_comment.CommentRepository;
import jpaproject.knockknock.repository.post_comment.PostRepository;
import jpaproject.knockknock.repository.post_comment.PostRepositorySupport;
import jpaproject.knockknock.api.request.CommentRequest;
import jpaproject.knockknock.api.request.PostSaveRequest;
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
    @Autowired
    PostRepositorySupport postRepositorySupport;

    @Before
    public void 초기데이터설정(){
        Member member = Member.memberBuilder().nickName("테스트멤버1").userId("testmember1").userPassword("1234").build();
        memberRepository.save(member);
        PostSaveRequest postSaveRequest = new PostSaveRequest("테스트초기데이터","안녕",member.getUserId(),"테스트초기",1.0F,1.0F,"이대도서관");

    }
    @Test
    public void 댓글달기(){
        //given
        List<Post> posts = postRepositorySupport.findByTag("테스트");
        Post post = posts.get(0);
        Member member = memberRepository.findByNickName("테스트멤버1").orElseThrow(()->new IllegalArgumentException("해당 멤버 없음"));
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
        List<Post> posts = postRepositorySupport.findByTag("영어");
        Member member = memberRepository.findByNickName("테스트멤버1").orElseThrow(()->new IllegalArgumentException("해당 멤버 없음"));
        Post post = posts.get(0);
        CommentRequest commentRequest = new CommentRequest(member.getUserId(),post.getId(),"this comment should be deleted");
        Comment savedComment= commentService.save(commentRequest);
        Long id = savedComment.getId();
        Comment target1 = commentRepository.findById(savedComment.getId()).orElseThrow(()->new IllegalArgumentException("해당 댓글 없음"));
        //when
        commentService.delete(savedComment.getId());
        //then
        Comment target2 = commentRepository.findById(savedComment.getId()).orElseThrow(()->new IllegalArgumentException("해당 댓글 없음"));
        Assertions.assertThat(target2).isNull();
        List<Post> posts2 = postRepositorySupport.findByTag("영어");
        Post post2 = posts2.get(0);
        List<Comment> comments = post2.getPostcomments();
        Assertions.assertThat(comments.size()).isEqualTo(0);
    }
}
