package jpaproject.knockknock.repository.message;

import jpaproject.knockknock.domain.message.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {


}
