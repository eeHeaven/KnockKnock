package jpaproject.knockknock;

import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.requestForm.CommentRequest;
import jpaproject.knockknock.requestForm.PostSaveRequest;
import jpaproject.knockknock.service.post_comment.CommentService;
import jpaproject.knockknock.service.post_comment.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@Transactional
@RequiredArgsConstructor
public class InitService {

    private final EntityManager em;
    private final PostService postService;
    private final CommentService commentService;

    public void dbInit1(){

        PostSaveRequest postSaveRequest = new PostSaveRequest();
        postSaveRequest.setTitle("안녕");
        postSaveRequest.setContent("반가워");
        String[] tags = {"인사","소개"};
        postSaveRequest.setHashTags(tags);

       PostSaveRequest postSaveRequest2 = new PostSaveRequest();
        postSaveRequest2.setTitle("안녕하세요");
        postSaveRequest2.setContent("반가워요");
        String[] tags2 = {"인사","소개","존댓말"};
        postSaveRequest2.setHashTags(tags2);

        PostSaveRequest postSaveRequest3 = new PostSaveRequest();
        postSaveRequest3.setTitle("잘가");
        postSaveRequest3.setContent("반가웠어");
        String[] tags3 = {"굿바이","인사"};
        postSaveRequest3.setHashTags(tags3);

        PostSaveRequest postSaveRequest4 = new PostSaveRequest();
        postSaveRequest4.setTitle("GoodBye");
        postSaveRequest4.setContent("goodbye");
        String[] tags4 = {"굿바이","인사","영어"};
        postSaveRequest4.setHashTags(tags4);

       Post post1 =  postService.save(postSaveRequest);
       Post post2 =  postService.save(postSaveRequest2);
        postService.save(postSaveRequest3);
       Post post3 =  postService.save(postSaveRequest4);

        CommentRequest commentRequest1 = new CommentRequest();
        commentRequest1.setComment("저도 오늘 가입했어요");
        commentRequest1.setPostId(post1.getId());

        CommentRequest commentRequest2 = new CommentRequest();
        commentRequest2.setComment("잘부탁드립니다^^");
        commentRequest2.setPostId(post2.getId());

        CommentRequest commentRequest3 = new CommentRequest();
        commentRequest3.setComment("hello");
        commentRequest3.setPostId(post3.getId());

        commentService.save(commentRequest1);
        commentService.save(commentRequest2);
        commentService.save(commentRequest3);




    }

}
