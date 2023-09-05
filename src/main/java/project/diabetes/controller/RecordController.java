package project.diabetes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.diabetes.domain.Member;
import project.diabetes.domain.Record;
import project.diabetes.service.RecordService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @GetMapping("/record/{memberId}")
    public String Record(Model model, @PathVariable Long memberId){
        model.addAttribute("memberId",memberId);
        return "record";
    }

    @PostMapping("/record/{memberId}/save")
    public String saveRecord(Record record, @PathVariable Long memberId, Model model) {
        Member member = recordService.findMemberByMemberId(memberId);
        model.addAttribute("memberId",memberId);
        model.addAttribute("member", member);
        recordService.saveRecord(record);

        List<String> recordlist = recordService.findAmountByMemberId(memberId);

        if (recordlist.size() <= 84) {
            return "redirect:/board/"+memberId; // board 템플릿으로 리다이렉트
        } else if (recordlist.size() <= 168){
            return "redirect:/board2/"+memberId; // board2 템플릿으로 리다이렉트
        } else{
            return "redirect:/board3/"+memberId; // board3 템플릿으로 리다이렉트
        }
    }


    @GetMapping("/board/{memberId}")
    public String board1(Model model, @PathVariable Long memberId) {
        model.addAttribute("memberId",memberId);
        List<Integer> numericRecordList = new ArrayList<>();
        List<String> recordlist = recordService.findAmountByMemberId(memberId);
        for (int i = 0; i < Math.min(84, recordlist.size()); i++) {
            String record = recordlist.get(i);
            try {
                numericRecordList.add(Integer.parseInt(record));
            } catch (NumberFormatException e) {
                numericRecordList.add(null);
            }
        }
        model.addAttribute("numericRecordList", numericRecordList);
        return "board";
    }


    @GetMapping("/board2/{memberId}")
    public String board2(Model model, @PathVariable Long memberId) {
        model.addAttribute("memberId",memberId);
        List<Integer> numericRecordList = new ArrayList<>();
        List<String> recordlist = recordService.findAmountByMemberId(memberId);
        for (int i = 84; i < Math.min(168, recordlist.size()); i++) {
            String record = recordlist.get(i);
            try {
                numericRecordList.add(Integer.parseInt(record));
            } catch (NumberFormatException e) {
                numericRecordList.add(null);
            }
        }
        model.addAttribute("numericRecordList", numericRecordList);
        return "board2";
    }

    @GetMapping("/board3/{memberId}")
    public String board3(Model model, @PathVariable Long memberId) {
        model.addAttribute("memberId",memberId);
        List<Integer> numericRecordList = new ArrayList<>();
        List<String> recordlist = recordService.findAmountByMemberId(memberId);
        for (int i = 168; i < recordlist.size(); i++) {
            String record = recordlist.get(i);
            try {
                numericRecordList.add(Integer.parseInt(record));
            } catch (NumberFormatException e) {
                numericRecordList.add(null);
            }
        }
        model.addAttribute("numericRecordList", numericRecordList);
        return "board3";
    }

    @GetMapping("/graph/{memberId}") //그래프
    public String graph(Model model, @PathVariable Long memberId) {
        model.addAttribute("memberId",memberId);
        List<String> glist = recordService.findGlucoseByMemberId(memberId);
        model.addAttribute("glist", glist);
        return "graph";
    }
}

