package jpaproject.knockknock.repository.message;

import jpaproject.knockknock.domain.message.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChatRoomRepository extends JpaRepository<UserChatRoom,Long> {
}
