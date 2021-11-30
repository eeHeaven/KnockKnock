package jpaproject.knockknock;

import jpaproject.knockknock.domain.post_comment.PostHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashTagRepository extends JpaRepository<PostHashTag, Long> {
}
