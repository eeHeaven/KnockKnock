package jpaproject.knockknock.repository.post_comment;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.post_comment.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepositoryCustom {
    List<Post> findByLocation(double latitude, double longitude);
    List<Post> findByTag(String tag);
}
