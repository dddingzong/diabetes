package project.diabetes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.diabetes.domain.Member;
import project.diabetes.domain.Record;
import project.diabetes.repository.RecordRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService{
    private final RecordRepository recordRepository;

    @Transactional
    public void saveRecord(Record record){
        recordRepository.saveRecord(record);
    }

    public List<String> findAmountByMemberId(Long memberId) {
        return recordRepository.findAmountByMemberId(memberId);
    }

    public List<String> findGlucoseByMemberId(Long memberId) {
        return recordRepository.findGlucoseByMemberId(memberId);
    }

    public Member findMemberByMemberId(Long memberId){
        return recordRepository.findMemberByMemberId(memberId);
    }
}
