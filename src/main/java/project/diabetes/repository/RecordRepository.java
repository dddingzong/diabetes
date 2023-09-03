package project.diabetes.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.diabetes.domain.Record;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class RecordRepository {

    private final EntityManager em;

    public Record findMemberByMemberId(Long memberId) {
        return em.createQuery("SELECT m FROM Member m WHERE m.id = :memberId", Record.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }

    public void saveRecord(Record record) {
        em.persist(record);
    }


}