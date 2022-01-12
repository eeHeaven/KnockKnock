package jpaproject.knockknock.repository.post_comment;

//import com.querydsl.core.support.FetchableQueryBase;
//import com.querydsl.core.support.QueryBase;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaproject.knockknock.domain.post_comment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HashTagRepository {
    private final EntityManager em;
  //  private final JPAQueryFactory queryFactory;

    public PostHashTag save(PostHashTag postHashTag){em.persist(postHashTag); return postHashTag;}
    public HashTag save(HashTag hashTag){em.persist(hashTag);return hashTag;}
    public HashTag findOne(Long id){return em.find(HashTag.class,id);}
    public List<HashTag> findAll(){return em.createQuery("select h from HashTag h",HashTag.class).getResultList();}
    public HashTag findByTag(String tag){
        try{
        return em.createQuery("select h from HashTag h " +
                "where h.tag = :tag",HashTag.class)
                .setParameter("tag",tag)
                .getSingleResult();}
        catch(NoResultException nre){
            return null;
        }
    }
  //  QPost post = QPost.post;
  //  QPostHashTag postHashTag = QPostHashTag.postHashTag;
  //  QHashTag hashTag = QHashTag.hashTag;

//   public List<HashTag> findBySearch(String input){
//        try{List<HashTag> tags = queryFactory.selectFrom(hashTag)
//                .where(hashTag.tag.like(input))
//                .fetch();
//        return tags;}
//        catch(NoResultException nre){return null;}
//   }

   public List<PostHashTag> findPostHashTags(Long id){
       try{
           return em.createQuery("select p from PostHashTag p " +
                   "where p.hashtag.id = :hashtag",PostHashTag.class)
                   .setParameter("hashtag",id)
                   .getResultList();
          /* List<PostHashTag> posthashtags = queryFactory.selectFrom(postHashTag)
               .where(postHashTag.hashTag.id.eq(id))
               .fetch();
           return posthashtags;*/}
       catch(NoResultException nre){return null;}
   }

   public void removeHashTag(HashTag hashTag){em.remove(hashTag);}

    @Transactional
    public void removePostHashTag(PostHashTag postHashTag){em.remove(postHashTag);}

}
