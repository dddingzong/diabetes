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

    public void alterMemberInfo(Member updatedMember) {
        Member existingMember = em.find(Member.class, updatedMember.getId());
        existingMember.setName(updatedMember.getName());
        existingMember.setAge(updatedMember.getAge());
        existingMember.setSex(updatedMember.getSex());
        existingMember.setHeight(updatedMember.getHeight());
        existingMember.setWeight(updatedMember.getWeight());
        existingMember.setGoal(updatedMember.getGoal());

        // 변경 내용을 데이터베이스에 반영
        em.merge(existingMember);
    }
}
