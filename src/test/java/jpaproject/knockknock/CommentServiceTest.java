package jpaproject.knockknock;

import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.repository.post_comment.PostRepository;
import jpaproject.knockknock.requestForm.CommentRequest;
import jpaproject.knockknock.service.post_comment.CommentService;
import jpaproject.knockknock.service.post_comment.PostService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    @Test
    public void 댓글달기(){
        //given
        List<Post> posts = postRepository.findByTag("영어");
        Post post = posts.get(0);
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setComment("nice to meet you");
        commentRequest.setPostId(post.getId());
        //when
        commentService.save(commentRequest);
        //then
        List<Comment> comments = post.getPostcomments();
        Comment comment = comments.get(1);
        Assertions.assertThat(comment.getContent()).isEqualTo("nice to meet you");
        Assertions.assertThat(comments.size()).isEqualTo(2);
    }

    @Test
    public void 댓글삭제(){
        //given
        List<Post> posts = postRepository.findByTag("영어");
        Post post = posts.get(0);
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setComment("this comment should be deleted");
        commentRequest.setPostId(post.getId());
        Comment thisComment = commentService.save(commentRequest);
        //when
        commentService.delete(thisComment.getId());
        //then
        List<Post> posts2 = postRepository.findByTag("영어");
        Post post2 = posts2.get(0);
        List<Comment> comments = post2.getPostcomments();
        Assertions.assertThat(comments.size()).isEqualTo(1);
    }
}
