package jpaproject.knockknock.repository;

import jpaproject.knockknock.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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

    public Member LoginMemberReturn(String id, String pw){
        return em.createQuery("select m from Member m where m.userId = :id and m.userPassword = :pw",Member.class)
                .setParameter("id",id)
                .setParameter("pw",pw)
                .getSingleResult();
    }



}
