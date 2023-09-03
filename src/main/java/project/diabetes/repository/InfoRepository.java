package project.diabetes.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.diabetes.domain.Member;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class InfoRepository {

    private final EntityManager em;

    public Member findMemberByMemberId(Long memberId){
        return em.createQuery("SELECT m FROM Member m WHERE m.id = :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }

    public void saveMemberInfo(Member member) {
        em.persist(member);
    }
}
