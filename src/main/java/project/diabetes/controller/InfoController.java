package project.diabetes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.diabetes.domain.Member;
import project.diabetes.service.InfoService;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class InfoController {

    private final InfoService infoService;


    @PostMapping("/info/{memberId}/save")
    public String saveInfo(Member member, @PathVariable Long memberId) {
        infoService.saveMemberInfo(memberId);
        return  "/info/{memberId}";
    }

    @PostMapping("/info/{memberId}/alter")
    public String alterInfo(Member member, @PathVariable Long memberId) {
        infoService.alterMemberInfo(member);
        return  "/info/{memberId}";
    }


    @GetMapping("/info/{memberId}")
    public String info(Model model, HttpSession session, @PathVariable Long memberId) {
        Member member = infoService.findMemberByMemberId(memberId);
        List<String> glist = (List<String>) session.getAttribute("glist"); // 세션에서 glist 가져오기
        model.addAttribute("glist", glist);
        model.addAttribute("memberId",memberId);

        if (member.getGoal() == null) {
            return "infoFirst";
        }
        return "/info/{memberId}";
    }
}
