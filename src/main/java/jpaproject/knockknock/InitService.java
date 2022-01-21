package jpaproject.knockknock;



import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@Transactional
@RequiredArgsConstructor
public class InitService {

    private final EntityManager em;
    private final MemberService memberService;

    public void dbInit1(){

//        Member member1 = new Member("테스트멤버1","testmember1","1234");
//        memberService.signIn(member1);

//        Member member1 = new Member();
//        SignInRequest signInRequest1 = new SignInRequest();
//        signInRequest1.setNickname("테스트멤버1");
//        signInRequest1.setId("test1");
//        signInRequest1.setPassword("1234");
//       member1 =  memberService.signIn(signInRequest1);
//
//
//        Member member2 = new Member();
//        SignInRequest signInRequest2 = new SignInRequest();
//        signInRequest2.setNickname("테스트멤버2");
//        signInRequest2.setId("test2");
//        signInRequest2.setPassword("1234");
//        member2 = memberService.signIn(signInRequest2);
//
//        PostSaveRequest postSaveRequest = new PostSaveRequest();
//        postSaveRequest.setTitle("테스트1의 게시글입니다");
//        postSaveRequest.setContent("반가워");
//        postSaveRequest.setWriterId(member1.getId());
//        String[] tags = {"테스트","인사"};
//        postSaveRequest.setHashTags(tags);
//
//       PostSaveRequest postSaveRequest2 = new PostSaveRequest();
//        postSaveRequest2.setTitle("안녕하세요");
//        postSaveRequest2.setContent("반가워요");
//        postSaveRequest2.setWriterId(member2.getId());
//        String[] tags2 = {"인사","소개","존댓말"};
//        postSaveRequest2.setHashTags(tags2);
//
//        PostSaveRequest postSaveRequest3 = new PostSaveRequest();
//        postSaveRequest3.setTitle("잘가");
//        postSaveRequest3.setContent("반가웠어");
//        postSaveRequest3.setWriterId(member1.getId());
//        String[] tags3 = {"굿바이","인사"};
//        postSaveRequest3.setHashTags(tags3);
//
//        PostSaveRequest postSaveRequest4 = new PostSaveRequest();
//        postSaveRequest4.setTitle("GoodBye");
//        postSaveRequest4.setContent("goodbye");
//        postSaveRequest4.setWriterId(member2.getId());
//        String[] tags4 = {"굿바이","인사","영어"};
//        postSaveRequest4.setHashTags(tags4);
//
//       Post post1 =  postService.save(postSaveRequest);
//       Post post2 =  postService.save(postSaveRequest2);
//       Post post3 =  postService.save(postSaveRequest3);
//       Post post4 =  postService.save(postSaveRequest4);
//
//        CommentRequest commentRequest1 = new CommentRequest();
//        commentRequest1.setComment("저도 오늘 가입했어요");
//        commentRequest1.setWriterId(member2.getId());
//        commentRequest1.setPostId(post1.getId());
//
//        CommentRequest commentRequest2 = new CommentRequest();
//        commentRequest2.setComment("잘부탁드립니다^^");
//        commentRequest2.setWriterId(member1.getId());
//        commentRequest2.setPostId(post2.getId());
//
//        commentService.save(commentRequest1);
//        commentService.save(commentRequest2);





    }

}
