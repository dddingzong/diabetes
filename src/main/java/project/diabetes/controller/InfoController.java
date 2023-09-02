package project.diabetes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static project.diabetes.repository.RecordsRepository.glist;

@Controller
public class InfoController {
    @GetMapping("/info/{memberId}")
    public String getInfoPage(Model model, @PathVariable String memberId) {
        model.addAttribute("glist", glist);

        System.out.println("{memberId} = " + memberId);

        return "info"; // info.html 템플릿으로 이동
    }
}
