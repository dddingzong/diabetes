package project.diabetes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.diabetes.domain.Login;
import project.diabetes.domain.Member;
import project.diabetes.service.LoginService;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/") //로그인 페이지
    public String home() {
        return "logIn";
    }

    @PostMapping("/")
    public String signUp(Model model, String userId, String userEmail, String userPassword, String checkPassword) {

        // 아이디가 중복된 경우 (true 면 중복)
        if (loginService.checkDuplicateId(userId)) {
            model.addAttribute("warning", "아이디가 중복되었습니다.");
            return "logIn";
        }

        //비밀번호와 비밀번호 확인이 다른경우
        if (!(userPassword.equals(checkPassword))) {
            model.addAttribute("warning", "비밀번호와 비밀번호 재입력이 다릅니다.");
            return "logIn";
        }

        Login user = new Login(userId, userEmail, userPassword);
        loginService.signUp(user);

        return "logIn";
    }

    @PostMapping("/real_login")
    public String logIn(Model model, String login_userId, String login_userPassword) {

        // 아이디가 존재하지 않는 경우 (true면 존재)
        if (!(loginService.checkNoUserId(login_userId))) {
            model.addAttribute("warning", "아이디가 존재하지 않습니다.");
            return "logIn";
        }

        // Login 객체에서 userId로 검색
        Login logincheck = loginService.login(login_userId);

        // 그에 맞는 userPassword 검색 (틀리면 없다고 경고문)
        if (!(login_userPassword.equals(logincheck.getUserPassword()))) {
            model.addAttribute("warning", "비밀번호가 틀렸습니다.");
            return "logIn";
        }

        // uesrId를 사용해 Member table 에서 member_id를 가져옴 (이제 모든 DB의 구분자)
        Member member = loginService.findMember(login_userId);
        Long member_id = member.getId();
        model.addAttribute("memberId", member_id);

        String originalUrl = "redirect:/info/";
        return originalUrl + member_id;
    }
}
