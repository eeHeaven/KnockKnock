package jpaproject.knockknock.repository.message;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.message.ChatRoom;
import jpaproject.knockknock.domain.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

}
