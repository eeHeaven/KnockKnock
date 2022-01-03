package jpaproject.knockknock.repository.message;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.message.QMessageDialog;
import jpaproject.knockknock.domain.message.QUserMessage;
import jpaproject.knockknock.domain.message.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserMessageRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    QMessageDialog messageDialog = QMessageDialog.messageDialog;
    QUserMessage userMessage = QUserMessage.userMessage;

    public void save(UserMessage userMessage){em.persist(userMessage);}

    public List<UserMessage> findBySender(Member sender){
        List<UserMessage> messageList = queryFactory.selectFrom(userMessage)
                .where(userMessage.senderId.eq(sender.getId()))
                .orderBy(userMessage.timestamp.desc())
                .fetch();
        return messageList;
    }

    public List<UserMessage> findByReceiver(Member receiver){
        List<UserMessage> messageList = queryFactory.selectFrom(userMessage)
                .where(userMessage.recieverId.eq(receiver.getId()))
                .orderBy(userMessage.timestamp.desc())
                .fetch();
        return messageList;
    }

    public List<UserMessage> findByMembers(Member sender, Member receiver){
        List<UserMessage> messageList = queryFactory.selectFrom(userMessage)
                .where(userMessage.senderId.eq(sender.getId()).and(userMessage.recieverId.eq(receiver.getId())))
                .orderBy(userMessage.timestamp.desc())
                .fetch();
        return messageList;
    }


}
