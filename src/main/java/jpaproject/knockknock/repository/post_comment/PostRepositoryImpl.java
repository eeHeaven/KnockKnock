package jpaproject.knockknock.repository.post_comment;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaproject.knockknock.domain.post_comment.Post;
import jpaproject.knockknock.domain.post_comment.QHashTag;
import jpaproject.knockknock.domain.post_comment.QPost;
import jpaproject.knockknock.domain.post_comment.QPostHashTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    QPost post = QPost.post;
    QPostHashTag postHashTag = QPostHashTag.postHashTag;
    QHashTag qhashTag = QHashTag.hashTag;


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

    //해시태그 기반 게시글 조회
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
}
