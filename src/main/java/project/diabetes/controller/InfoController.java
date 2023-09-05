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

    @GetMapping("/info/{memberId}")
    public String info(Model model, HttpSession session, @PathVariable Long memberId) {
        Member member = infoService.findMemberByMemberId(memberId);
        List<String> glist = (List<String>) session.getAttribute("glist"); // 세션에서 glist 가져오기
        model.addAttribute("glist", glist);
        model.addAttribute("memberId",memberId);
        model.addAttribute("member", member);

        if (member.getGoal()  == null) {
            return "info";
        }
        return "infoFirst";
    }

    @GetMapping("/infoFirst/{memberId}")
    public String infoFirst(Model model, @PathVariable Long memberId) {
        Member member = infoService.findMemberByMemberId(memberId);
        model.addAttribute("memberId",memberId);
        model.addAttribute("member",member);
        return "info";
    }
    @PostMapping("/infoFirst/{memberId}/save")
    public String saveMemberInfo(Model model, @PathVariable Long memberId,
                                 @RequestParam String name,
                                 @RequestParam int age,
                                 @RequestParam String sex,
                                 @RequestParam float height,
                                 @RequestParam float weight,
                                 @RequestParam Integer goal) {
        Member member = infoService.findMemberByMemberId(memberId);
        model.addAttribute("memberId",memberId);
        model.addAttribute("member",member);

            member.setName(name);
            member.setAge(age);
            member.setSex(sex);
            member.setHeight(height);
            member.setWeight(weight);
            member.setGoal(goal);

            infoService.saveMember(member);
            infoService.flush();

        return "redirect:calculator/" + memberId;
    }
}
