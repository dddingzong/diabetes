package project.diabetes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.diabetes.domain.Record;
import project.diabetes.service.RecordService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecordController {

    List<String> recordlist = new ArrayList<>();
    List<String> glist = new ArrayList<>();

    private final RecordService recordService;

    @PostMapping("/record/{memberId}/save")
    public String createRecord(Record record, @PathVariable Long memberId, Model model) {
        recordService.saveRecord(record);

        //recordlist로 amount, glucose 가져옴
        recordlist.add(String.valueOf(record.getAmount()));
        recordlist.add(String.valueOf(record.getGlucose()));

        //glist로 glucose만 가져옴
        glist.add(String.valueOf(record.getGlucose()));
        model.addAttribute("glist", glist);


        if (recordlist.size() <= 84) {
            return "redirect:/board/"+memberId; // board 템플릿으로 리다이렉트
        } else if (recordlist.size() <= 168){
            return "redirect:/board2/"+memberId; // board2 템플릿으로 리다이렉트
        } else{
            return "redirect:/board3/"+memberId; // board3 템플릿으로 리다이렉트
        }
    }

    @GetMapping("/board/{memberId}")
    public String myPage(Model model) {
        List<Integer> numericRecordList = new ArrayList<>();
        for (int i = 0; i < Math.min(84, recordlist.size()); i++) {
            String record = recordlist.get(i);
            try {
                numericRecordList.add(Integer.parseInt(record));
            } catch (NumberFormatException e) {
                numericRecordList.add(null);
            }
        }
        model.addAttribute("numericRecordList", numericRecordList);
        return "board/{memberId}";
    }
    @GetMapping("/board2/{memberId}")
    public String myPage2(Model model) {
        List<Integer> numericRecordList = new ArrayList<>();
        for (int i = 84; i < Math.min(168, recordlist.size()); i++) {
            String record = recordlist.get(i);
            try {
                numericRecordList.add(Integer.parseInt(record));
            } catch (NumberFormatException e) {
                numericRecordList.add(null);
            }
        }
        model.addAttribute("numericRecordList", numericRecordList);
        return "board2/{memberId}";
    }

    @GetMapping("/board3/{memberId}")
    public String myPage3(Model model) {
        List<Integer> numericRecordList = new ArrayList<>();
        for (int i = 168; i < recordlist.size(); i++) {
            String record = recordlist.get(i);
            try {
                numericRecordList.add(Integer.parseInt(record));
            } catch (NumberFormatException e) {
                numericRecordList.add(null);
            }
        }
        model.addAttribute("numericRecordList", numericRecordList);
        return "board2/{memberId}";
    }

    @GetMapping("/graph/{memberId}") //그래프
    public String graph(Model model) {
        model.addAttribute("glist", glist);
        return "graph/{memberId}";
    }
}

