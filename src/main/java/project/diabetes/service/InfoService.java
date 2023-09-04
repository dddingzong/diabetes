package project.diabetes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.diabetes.domain.Member;
import project.diabetes.repository.InfoRepository;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final InfoRepository infoRepository;

    @Transactional
    public void saveMemberInfo(String name,int age, String sex, float height, float weight,Integer goal, Long memberId) {
        infoRepository.saveMemberInfo(name,age,sex,height,weight,goal, memberId);
    }

    public Member findMemberByMemberId(Long memberId) {
        return infoRepository.findMemberByMemberId(memberId);
    }

}