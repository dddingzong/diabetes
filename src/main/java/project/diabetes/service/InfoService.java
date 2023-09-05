package project.diabetes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.diabetes.domain.FoodRecord;
import project.diabetes.domain.Login;
import project.diabetes.domain.Member;
import project.diabetes.domain.Record;
import project.diabetes.repository.InfoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final InfoRepository infoRepository;


    @Transactional
    public List<Member> findAllMember() {
        return infoRepository.findAllMember();
    }

    public Member findMemberByMemberId(Long memberId) {
        return infoRepository.findMemberByMemberId(memberId);
    }

    @Transactional
    public void flush() {
        infoRepository.flush();
    }

    public Login findLoginByUserId(String userId) {
        return infoRepository.finLoginByUserId(userId);
    }
}