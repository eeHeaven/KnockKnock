package jpaproject.knockknock.repository.post_comment;

//import com.querydsl.core.support.FetchableQueryBase;
//import com.querydsl.core.support.QueryBase;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaproject.knockknock.domain.post_comment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag,Long> {
    Optional<HashTag> findByTag(String tag);
}
