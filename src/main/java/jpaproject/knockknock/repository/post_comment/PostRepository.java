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
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Query;
 import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
 import javax.persistence.NoResultException;
 import java.util.ArrayList;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

 List<Post> findByMember(Long id);
}
