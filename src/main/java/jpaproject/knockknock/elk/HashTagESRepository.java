package jpaproject.knockknock.elk;

import jpaproject.knockknock.domain.post_comment.HashTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashTagESRepository extends ElasticsearchRepository<HashTag,Long> {
    @Query("{\"match\":{\"tag\":\"?0\"}}")
    List<HashTag> findByTag(String input);
}
