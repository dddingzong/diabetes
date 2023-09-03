package project.diabetes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.diabetes.domain.Record;
import project.diabetes.repository.RecordRepository;

@Service
@RequiredArgsConstructor
public class RecordService{
    private final RecordRepository recordRepository;

    @Transactional
    public void saveRecord(Record record){
        recordRepository.saveRecord(record);
    }
    public Record findMemberByMemberId(Long memberId){
        return recordRepository.findMemberByMemberId(memberId);
    }

}
