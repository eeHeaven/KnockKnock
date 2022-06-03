package jpaproject.knockknock;

import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.service.MemberService;
import jpaproject.knockknock.service.post_comment.HashTagService;
import jpaproject.knockknock.service.post_comment.PostService;
import org.assertj.core.api.Assertions;
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
public class HashTagTest {

    @Autowired
    MemberService memberService;
    @Autowired
    PostService postService;
    @Autowired
    HashTagService hashTagService;


    @Test
    public void 해시태그로postList조회(){
        //when
        List<Post> posts= hashTagService.PostListByTag("hi");
        //then
        Assertions.assertThat(posts.size()).isEqualTo(3);
    }

}
