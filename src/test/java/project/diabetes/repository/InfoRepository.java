package project.diabetes.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.diabetes.domain.FoodRecord;
import project.diabetes.domain.Login;
import project.diabetes.domain.Member;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InfoRepository {

    private final EntityManager em;

    public Member findMemberByMemberId(Long memberId) {
        return em.createQuery("SELECT m FROM Member m WHERE m.id = :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }


    public void flush(){
        em.flush();
    }

    public List<Member> findAllMember() {
        return em.createQuery("select r from Member r")
                .getResultList();
    }

    public Login finLoginByUserId(String userId) {
        return em.createQuery("select l from Login l where l.userId=:userId",Login.class)
                .setParameter("userId",userId)
                .getSingleResult();
    }
}