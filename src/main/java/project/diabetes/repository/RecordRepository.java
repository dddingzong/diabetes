package project.diabetes.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.diabetes.domain.Member;
import project.diabetes.domain.Record;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class RecordRepository {

    private final EntityManager em;

    public Member findMemberByMemberId(Long memberId){
        return em.createQuery("SELECT m FROM Member m WHERE m.id = :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }

    public void saveRecord(Record record) {
        em.persist(record);
    }

    public List<Record> findRecordByMemberId(Long memberId) {
        return em.createQuery("SELECT r FROM Record r where r.member_id=:member_id Order By id DESC", Record.class)
                .setParameter("member_id", memberId)
                .getResultList();
    }

    public List<String> findGlucoseByMemberId(Long memberId) {
        return (List<String>) em.createQuery("SELECT m FROM Member m WHERE m.id = :memberId", Record.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
}