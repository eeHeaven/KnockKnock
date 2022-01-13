package jpaproject.knockknock.repository.message;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.QMember;
import jpaproject.knockknock.domain.message.MessageDialog;
import jpaproject.knockknock.domain.message.QMessageDialog;
import jpaproject.knockknock.domain.message.QUserMessage;
import jpaproject.knockknock.domain.message.UserMessage;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageDialogRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    QUserMessage userMessage = QUserMessage.userMessage;
    QMessageDialog messageDialog = QMessageDialog.messageDialog;
    QMember member = QMember.member;

    public void save(MessageDialog messageDialog){em.persist(messageDialog);}

    public MessageDialog findByMembers(Member member, Long partner){
        try{MessageDialog dialog = queryFactory.selectFrom(messageDialog)
                .where(messageDialog.member.eq(member).and(messageDialog.partnerId.eq(partner)))
                .fetchOne();
        return dialog;}
        catch(NoResultException nre){ return null;}
    }

}
