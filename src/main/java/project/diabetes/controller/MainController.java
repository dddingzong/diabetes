package project.diabetes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/first") // 처음 로그인 데이터 받기
    public String about() {
        return "first";
    }

    @GetMapping("/record") //혈당등록 페이지
    public String record() {
        return "record";
    }
}
