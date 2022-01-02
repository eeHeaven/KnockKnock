package jpaproject.knockknock.repository;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.requestForm.LoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save (Member member){
        em.persist(member);
    }
    public Member findOne(Long id){
        return em.find(Member.class,id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }

    public List<Member> findByNickName(String nickname){
        return em.createQuery("select m from Member m where m.nickName = :nickname",Member.class)
                .setParameter("nickname",nickname)
                .getResultList();
    }

    public Member LoginMemberReturn(String id, String password){
        try{return em.createQuery("select m from Member m where m.userId = :id and m.userPassword = :pw",Member.class)
                .setParameter("id",id)
                .setParameter("pw",password)
                .getSingleResult();}
        catch(NoResultException nre){
            return null;
        }
    }

    public Member findByUserId(String id){
        try{
        return em.createQuery("select m from Member m where m.userId = :id",Member.class)
                .setParameter("id",id)
                .getSingleResult();}
        catch(NoResultException nre){
            return null;
        }
    }

    @Transactional
    public void remove(Member member){
        em.remove(member);
        em.flush();
    }



}
