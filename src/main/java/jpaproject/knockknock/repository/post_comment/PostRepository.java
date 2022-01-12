package jpaproject.knockknock.repository.post_comment;

// import com.querydsl.core.support.FetchableQueryBase;
//import com.querydsl.core.support.QueryBase;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;
   // private final JPAQueryFactory queryFactory;

    public Post save(Post post){em.persist(post); return post;}
    public Post findOneById(Long id){return em.find(Post.class, id);}
    public List<Post> findAll(){return em.createQuery("select p from Post p",Post.class).getResultList();}

//    QPost post = QPost.post;
//    QPostHashTag postHashTag = QPostHashTag.postHashTag;
//    QHashTag qhashTag = QHashTag.hashTag;
//
//    public List<Post> findByTag(String tag){
//
//        List<Post> posts = queryFactory.selectFrom(post).distinct()
//                .innerJoin(postHashTag).on(post.id.eq(postHashTag.post.id))
//                .where(postHashTag.tag.contains(tag))
//                .orderBy(post.timestamp.desc())
//                .fetch();
//        return posts;
//    }
//
//    public List<Post> findByMember(Long memberId){
//
//        List<Post> posts = queryFactory.selectFrom(post)
//                .where(post.postwriter.id.eq(memberId))
//                .orderBy(post.timestamp.desc())
//                .fetch();
//        return posts;
//    }

    public void remove(Post post){ em.remove(post);
    }





}
