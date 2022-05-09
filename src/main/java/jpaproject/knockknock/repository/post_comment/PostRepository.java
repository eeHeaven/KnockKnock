package jpaproject.knockknock.repository.post_comment;

 import com.querydsl.core.support.FetchableQueryBase;
import com.querydsl.core.support.QueryBase;
 import com.querydsl.core.types.Expression;
 import com.querydsl.core.types.dsl.Expressions;
 import com.querydsl.core.types.dsl.MathExpressions;
 import com.querydsl.core.types.dsl.NumberExpression;
 import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.*;
import lombok.RequiredArgsConstructor;
 import org.springframework.data.jpa.repository.Query;
 import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
 import javax.persistence.NoResultException;
 import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Post save(Post post){em.persist(post); return post;}
    public Post findOneById(Long id){return em.find(Post.class, id);}
    public List<Post> findAll(){return em.createQuery("select p from Post p",Post.class).getResultList();}

    QPost post = QPost.post;
    QPostHashTag postHashTag = QPostHashTag.postHashTag;
    QHashTag qhashTag = QHashTag.hashTag;

    public List<Post> findByTag(String tag){

        try{List<Post> posts = queryFactory.selectFrom(post).distinct()
                .innerJoin(postHashTag).on(post.id.eq(postHashTag.post.id))
                .where(postHashTag.tag.contains(tag))
                .orderBy(post.timestamp.desc())
                .fetch();
        return posts;}
        catch(NoResultException nre){
            return null;
        }
    }

    public List<Post> findByMember(String memberId){

       try{ List<Post> posts = queryFactory.selectFrom(post)
                .where(post.postwriter.userId.eq(memberId))
                .orderBy(post.timestamp.desc())
                .fetch();
        return posts;}
       catch(NoResultException nre){
           return null;
       }
    }

    public void remove(Post post){ em.remove(post);
    }


    //위치 기반으로 post 조회
    public List<Post> findByLocation(double latitude, double longitude){
        try{ List<Post> posts = queryFactory.selectFrom(post)
                .where(MathExpressions.acos(MathExpressions.sin(MathExpressions.radians(Expressions.constant(latitude)))
                        .multiply(MathExpressions.sin(MathExpressions.radians(post.latitude)))
                        .add(MathExpressions.cos(MathExpressions.radians(Expressions.constant(latitude)))
                                .multiply(MathExpressions.cos(MathExpressions.radians(post.latitude)))
                                .multiply(MathExpressions.cos(MathExpressions.radians(Expressions.constant(longitude)).subtract(MathExpressions.radians(post.longitude)))))).multiply(6371).loe(1))
                .orderBy(post.timestamp.desc())
                .fetch();
            return posts;
        }catch (NoResultException nre){
            return null;
        }
    }
}
