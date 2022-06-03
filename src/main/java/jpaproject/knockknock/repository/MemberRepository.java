package jpaproject.knockknock.repository;

import jpaproject.knockknock.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {


    Optional<Member> findByUserId(String userId);
    Optional<Member> findByNickName(String nickname);
    Optional<Member> findByUserIdAndUserPassword(String userId, String userPassword);
}
