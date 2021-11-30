package jpaproject.knockknock.repository;

import com.querydsl.core.support.FetchableQueryBase;
import com.querydsl.core.support.QueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaproject.knockknock.domain.post_comment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HashTagRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(HashTag hashTag){em.persist(hashTag);}
    public HashTag findOne(Long id){return em.find(HashTag.class,id);}
    public List<HashTag> findAll(){return em.createQuery("select h from HashTag h",HashTag.class).getResultList();}
    public HashTag findByTag(String tag){
        return em.createQuery("select h from HashTag h " +
                "where h.tag = :tag",HashTag.class)
                .setParameter("tag",tag)
                .getSingleResult();
    }
    QPost post = QPost.post;
    QPostHashTag postHashTag = QPostHashTag.postHashTag;
    QHashTag hashTag = QHashTag.hashTag;

    public List<PostHashTag> findPostHahTags(Long postid){
        List<PostHashTag> tags = queryFactory.selectFrom(postHashTag)
                .innerJoin(post).on(post.id.eq(postHashTag.post.id))
                .where(post.id.eq(postid))
                .fetch();
        return tags;
    }

   public List<HashTag> findBySearch(String input){
        List<HashTag> tags = queryFactory.selectFrom(hashTag)
                .where(hashTag.tag.like(input))
                .fetch();
        return tags;
   }


}
