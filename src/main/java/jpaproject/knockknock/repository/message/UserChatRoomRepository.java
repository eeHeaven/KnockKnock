package jpaproject.knockknock.repository.message;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaproject.knockknock.domain.QMember;
import jpaproject.knockknock.domain.message.QUserChatRoom;
import jpaproject.knockknock.domain.message.UserChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserChatRoomRepository{
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;
    QUserChatRoom userChatRoom =QUserChatRoom.userChatRoom;
    QMember member = QMember.member;

    public UserChatRoom getById(Long id){
        return queryFactory.selectFrom(userChatRoom)
                .where(userChatRoom.id.eq(id))
                .fetchOne();
    }
    public UserChatRoom save(UserChatRoom userChatRoom){
        em.persist(userChatRoom);
        return userChatRoom;
    }

    public List<UserChatRoom> findAll(){
        return queryFactory.selectFrom(userChatRoom).fetch();
    }

    public List<UserChatRoom> findByMemberId(String memberId){
        List<UserChatRoom> userChatRoomList;
        try{
            return queryFactory.selectFrom(userChatRoom)
                    .where(userChatRoom.member.userId.eq(memberId))
                    .orderBy(userChatRoom.chatRoom.lastMessageTimeStamp.desc())
                    .fetch();
        }
        catch(NoResultException nre){
            return null;
        }
    }
}
