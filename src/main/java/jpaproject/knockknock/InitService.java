package jpaproject.knockknock;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.HashTag;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.domain.post_comment.PostHashTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@Transactional
@RequiredArgsConstructor
public class InitService {

    private final EntityManager em;
    public void dbInit1(){
        Post post1 = createPost("안녕","반가워","인사");
        em.persist(post1);
        post1.addHashTag("굿바이");
        em.persist(post1);
        Post post2 = createPost("안녕하세요","반갑습니다","인사");
        em.persist(post2);
        Post post3 = createPost("잘가","안녕","굿바이");
        em.persist(post3);
        Post post4 = createPost("GoodBye","안녕","굿바이");
        em.persist(post4);
        HashTag hashTag1 = createHashTag("인사");
        em.persist(hashTag1);
        HashTag hashTag2 = createHashTag("굿바이");
        em.persist(hashTag2);


    }
    private Post createPost(String title, String content, String tag){
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        PostHashTag postHashTag = new PostHashTag();
        postHashTag.setTag(tag);
        post.addHashTag(postHashTag.getTag());
        return post;
    }

    private HashTag createHashTag(String tag){
        HashTag hashTag = new HashTag();
        hashTag.setTag(tag);
        return hashTag;
    }
}
