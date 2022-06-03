package jpaproject.knockknock.repository.post_comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
//import jpaproject.knockknock.domain.QMember;
import jpaproject.knockknock.domain.post_comment.Comment;
//import jpaproject.knockknock.domain.post_comment.QComment;
//import jpaproject.knockknock.domain.post_comment.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

}
