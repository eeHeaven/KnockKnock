package jpaproject.knockknock.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaproject.knockknock.domain.post_comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(Comment comment){em.persist(comment);}

}
