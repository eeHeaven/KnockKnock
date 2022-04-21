package jpaproject.knockknock;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.HashTag;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.elk.HashTagESRepository;
import jpaproject.knockknock.elk.HashTagESService;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.post_comment.HashTagRepository;
import jpaproject.knockknock.requestForm.PostSaveRequest;
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

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class ElasticSearchTest {
    @Resource
    HashTagESRepository hashTagESRepository;
    @Autowired
    HashTagESService hashTagESService;
    @Autowired
    HashTagService hashTagService;
    @Autowired
    PostService postService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    HashTagRepository hashTagRepository;

    @Test
    public void 해시태그저장(){
        //given
        HashTag hashTag = new HashTag();
        hashTag.setTag("엘라스틱서치");
        //when
        hashTagESService.saveHashtag(hashTag);
    }

    @Test
    public void 해시태그서비스저장(){
        //given
        HashTag hashTag = new HashTag();
        hashTag.setTag("해시태그서비스2");
        //when
        hashTagService.save(hashTag);
    }
    @Test
    public void 기존기능해시태그저장()throws IndexOutOfBoundsException, IOException {
        //given
        Member writer = memberRepository.findByNickName("테스트멤버1");
        PostSaveRequest postSaveRequest = new PostSaveRequest("테스트","테스트용글입니다.",writer.getUserId(),"포스트테스트",1.1F,1.1F,"이대도서관");
        //when
        Post savedPost =  postService.save(postSaveRequest);

    }
    @Test
    @Rollback(false)
    public void 해시태그삭제() throws IOException {
        Member writer = memberRepository.findByNickName("테스트멤버1");
        PostSaveRequest postSaveRequest = new PostSaveRequest("테스트","테스트용글입니다.",writer.getUserId(),"삭제테스트",1.1F,1.1F,"이대도서관");
        Post savedPost = postService.save(postSaveRequest);
        HashTag hashTag = hashTagRepository.findByTag("삭제테스트");
        hashTagESService.deleteHashtag(hashTag);
    }
    @Test
    @Rollback(false)
    public void 해시태그가사라질때elasticsearch에서도삭제() throws IOException {
        //given
        Member writer = memberRepository.findByNickName("테스트멤버1");
        PostSaveRequest postSaveRequest = new PostSaveRequest("테스트","테스트용글입니다.",writer.getUserId(),"삭제테스트",1.1F,1.1F,"이대도서관");
        Post savedPost = postService.save(postSaveRequest);
        //when
        postService.delete(savedPost.getId());
        //then
    }

    @Test
    public void 해시태그조회() throws IOException {
        //given
        Member writer = memberRepository.findByNickName("테스트멤버1");
        PostSaveRequest postSaveRequest = new PostSaveRequest("테스트","테스트용글입니다.",writer.getUserId(),"검색테스트",1.1F,1.1F,"이대도서관");
        Post savedPost = postService.save(postSaveRequest);
        PostSaveRequest postSaveRequest2 = new PostSaveRequest("테스트","테스트용글입니다.",writer.getUserId(),"가나다라사마바",1.1F,1.1F,"이대도서관");
        Post savedPost2 = postService.save(postSaveRequest2);
        //when
        List<HashTag> hashTags = hashTagESService.findHashTagbyinput("ㄱ");
        List<HashTag> hashTags2 = hashTagESService.findHashTagbyinput("가ㄴ");
        //then
        Assertions.assertThat(hashTags.size()).isEqualTo(2);
        Assertions.assertThat(hashTags2.size()).isEqualTo(1);
    }
}
