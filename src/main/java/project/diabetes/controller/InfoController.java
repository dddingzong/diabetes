package project.diabetes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.diabetes.domain.Member;
import project.diabetes.service.InfoService;

import static project.diabetes.repository.RecordsRepository.glist;

@Controller
@RequiredArgsConstructor
public class InfoController {

    private final InfoService infoService;

    @GetMapping("/info/{memberId}")
    public String info(Model model, @PathVariable Long memberId) {
        Member member = infoService.findMemberByMemberId(memberId);
        model.addAttribute("glist", glist);
        model.addAttribute("memberId",memberId);

//        if (member.getGoal() == null) {
//            return "infoFirst";
//        }

        return "info";
    }
}
