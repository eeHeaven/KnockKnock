package jpaproject.knockknock.repository.post_comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaproject.knockknock.domain.QMember;
import jpaproject.knockknock.domain.post_comment.Comment;
import jpaproject.knockknock.domain.post_comment.QComment;
import jpaproject.knockknock.domain.post_comment.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    @PersistenceContext
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(Comment comment){em.persist(comment);}
    public Comment findOne(Long id){return em.find(Comment.class,id);}

    QMember member = QMember.member;
    QComment comment = QComment.comment;
    QPost post = QPost.post;

    public List<Comment> findByMember(Long memberId){

        List<Comment> comments = queryFactory.selectFrom(comment)
                .innerJoin(member).on(comment.commentwriter.id.eq(member.id))
                .where(comment.commentwriter.id.eq(memberId))
                .orderBy(comment.timestamp.desc())
                .fetch();
        return comments;
    }

    public void remove(Comment comment){
        em.remove(comment);
        //em.flush();
    }

}
