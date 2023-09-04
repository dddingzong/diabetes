package project.diabetes.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.diabetes.domain.Member;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@RequiredArgsConstructor
public class InfoRepository {

    private final EntityManager em;

    public Member findMemberByMemberId(Long memberId) {
        return em.createQuery("SELECT m FROM Member m WHERE m.id = :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }

    @Transactional
    public void saveMemberInfo(String name,int age, String sex, float height, float weight,Integer goal, Long memberId) {
        Long id = em.createQuery("SELECT m FROM Member m WHERE m.id = :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getSingleResult().getId();

        Member member = em.find(Member.class, id);

        // 엔티티의 필드 값을 업데이트

        member.setName(name);
        member.setAge(age);
        member.setSex(sex);
        member.setHeight(height);
        member.setWeight(weight);
        member.setGoal(goal);

        em.persist(member);
    }

}


