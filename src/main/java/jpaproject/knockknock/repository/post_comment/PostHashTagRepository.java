package jpaproject.knockknock.repository.post_comment;

import jpaproject.knockknock.domain.post_comment.PostHashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostHashTagRepository extends JpaRepository<PostHashTag,Long> {
    List<PostHashTag> findByHashTag(Long id);
}