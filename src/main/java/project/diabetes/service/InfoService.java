package project.diabetes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.diabetes.domain.Member;
import project.diabetes.repository.InfoRepository;

@Service
@RequiredArgsConstructor
public class InfoService{
        private final InfoRepository infoRepository;

        @Transactional
        public void saveMemberInfo(Member member){
            infoRepository.saveMemberInfo(member);
        }
        public Member findMemberByMemberId(Long memberId){
            return infoRepository.findMemberByMemberId(memberId);
        }

        public void alterMemberInfo(Member member) {
            infoRepository.alterMemberInfo(member);
        }
}
