package jpaproject.knockknock;

import jpaproject.knockknock.domain.post_comment.HashTag;
import jpaproject.knockknock.domain.post_comment.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository  extends JpaRepository<Post, Long> {
}
