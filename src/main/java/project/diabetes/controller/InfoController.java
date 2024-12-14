package project.diabetes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.diabetes.domain.Login;
import project.diabetes.domain.Member;
import project.diabetes.service.InfoService;

import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
public class InfoController {

    private final InfoService infoService;

    @GetMapping("/info/{memberId}")
    public String info(Model model, HttpSession session, @PathVariable Long memberId) {
        Member member = infoService.findMemberByMemberId(memberId);

//        List<String> glist = (List<String>) session.getAttribute("glist"); // 세션에서 glist 가져오기
//        model.addAttribute("glist", glist);

        model.addAttribute("memberId", memberId);
        model.addAttribute("member", member);

        if (member.getGoal() == null) {
            return "infoFirst";
        }

        return "info";
    }

    @PostMapping("info/update/{memberId}") // 비밀번호랑 goal 변경 메소드
    public String updateMemberInfo(Model model, @PathVariable Long memberId, String userPassword, Integer goal) {
        model.addAttribute("memberId", memberId);

        Member member = infoService.findMemberByMemberId(memberId);
        String userId = member.getUserId();

        // goal 변경 (member table)
        member.setGoal(goal);
        // 비밀번호 변경 (login table + member table)
        member.setUserPassword(userPassword);
        //userId를 사용해서 login table 에서 login 객체 추출 후 userPassword 변경
        Login login = infoService.findLoginByUserId(userId);
        login.setUserPassword(userPassword);

        infoService.flush();

        model.addAttribute("member", member);
        return "info";
    }


    @GetMapping("/infoFirst/{memberId}") //존재이유 x
    public String infoFirst(Model model, @PathVariable Long memberId) {
        Member member = infoService.findMemberByMemberId(memberId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("member", member);
        return "info";
    }

    @PostMapping("/infoFirst/save/{memberId}")
    public String saveMemberInfo(Model model,
                                 @PathVariable Long memberId,
                                 String name,
                                 int age,
                                 String sex,
                                 float height,
                                 float weight,
                                 Integer goal) {
        Member member = infoService.findMemberByMemberId(memberId);
        model.addAttribute("memberId", memberId);

        member.setName(name);
        member.setAge(age);
        member.setSex(sex);
        member.setHeight(height);
        member.setWeight(weight);
        member.setGoal(goal);

        model.addAttribute("member", member);

        infoService.flush();

        return "info";
    }
}
