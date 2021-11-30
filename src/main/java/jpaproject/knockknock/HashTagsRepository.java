package jpaproject.knockknock;

import jpaproject.knockknock.domain.post_comment.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagsRepository extends JpaRepository<HashTag, Long> {

    HashTag findByTag(String tag);
}
