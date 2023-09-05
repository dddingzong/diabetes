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
        Member member = recordService.findMemberByMemberId(memberId);

        int recentAmount = member.getRecentAmount();
        model.addAttribute("recentAmount",recentAmount);

        return "record";
    }

    @PostMapping("/record/save/{memberId}")
    public String saveRecord(@PathVariable Long memberId, Model model, Integer amount, Integer glucose) {
        Member member = recordService.findMemberByMemberId(memberId);
        model.addAttribute("memberId",memberId);
        model.addAttribute("member", member);

        int CarbohydrateSum = 0;

        if (amount == null){
            amount = -1;
        }

        if (glucose == null){
            glucose = -1;
        }

        Record record = new Record(amount, glucose, CarbohydrateSum, memberId);

        recordService.saveRecord(record);

        List<Record> recordlist = recordService.findRecordByMemberId(memberId);

        List<Integer> amountList = new ArrayList<Integer>();
        for (int i=0; i<recordlist.size();i++){
            int amountForList = recordlist.get(i).getAmount();
            amountList.add(amountForList);
        }

        //// result table 에 데이터 삽입
        if ((amount!=null)&&(glucose!=null)){

        }


        if (amountList.size() <= 84) {
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
        List<Record> recordlist = recordService.findRecordByMemberId(memberId); //Record
        List<Integer> amountList = new ArrayList<>(); // Amount
        List<Integer> glucoseList = new ArrayList<>(); // Glucose

        List<Integer> numericAmountList = new ArrayList<>(); // model 로 넘어가는 리스트 (amount)
        List<Integer> numericGlucoseList = new ArrayList<>(); // model 로 넘어가는 리스트 (glucose)

        for (int i=0; i<recordlist.size();i++){
            int amountForList = recordlist.get(i).getAmount();
            amountList.add(amountForList);
        }

        for (int i=0; i<recordlist.size();i++){
            int glucoseForList = recordlist.get(i).getGlucose();
            glucoseList.add(glucoseForList);
        }


        for (int i = 0; i < Math.min(84, amountList.size()); i++) {
            int amount = amountList.get(i);

            try {
                numericAmountList.add(amount);
            } catch (NumberFormatException e) {
                numericAmountList.add(null);
            }
        }

        for (int i = 0; i < Math.min(84, glucoseList.size()); i++) {

            int glucose = glucoseList.get(i);

            try {
                numericGlucoseList.add(glucose);
            } catch (NumberFormatException e) {
                numericGlucoseList.add(null);
            }
        }

        List<Integer> totalList = new ArrayList<>();
        for (int i=0; i<recordlist.size();i++){
            totalList.add(numericAmountList.get(i));
            totalList.add(numericGlucoseList.get(i));
        }

        model.addAttribute("numericAmountList", numericAmountList);
        model.addAttribute("numericRecordList", numericGlucoseList);
        model.addAttribute("totalList",totalList);
        return "board";
    }


    @GetMapping("/board2/{memberId}")
    public String board2(Model model, @PathVariable Long memberId) {
        model.addAttribute("memberId",memberId);
        List<Record> recordlist = recordService.findRecordByMemberId(memberId); //Record
        List<Integer> amountList = new ArrayList<>(); // Amount
        List<Integer> glucoseList = new ArrayList<>(); // Glucose

        List<Integer> numericAmountList = new ArrayList<>(); // model 로 넘어가는 리스트 (amount)
        List<Integer> numericGlucoseList = new ArrayList<>(); // model 로 넘어가는 리스트 (glucose)

        for (int i=0; i<recordlist.size();i++){
            int amountForList = recordlist.get(i).getAmount();
            amountList.add(amountForList);
        }

        for (int i=0; i<recordlist.size();i++){
            int glucoseForList = recordlist.get(i).getGlucose();
            glucoseList.add(glucoseForList);
        }


        for (int i = 84; i < Math.min(168, amountList.size()); i++) {
            int amount = amountList.get(i);

            try {
                numericAmountList.add(amount);
            } catch (NumberFormatException e) {
                numericAmountList.add(null);
            }
        }

        for (int i = 84; i < Math.min(168, glucoseList.size()); i++) {

            int glucose = glucoseList.get(i);

            try {
                numericGlucoseList.add(glucose);
            } catch (NumberFormatException e) {
                numericGlucoseList.add(null);
            }
        }

        List<Integer> totalList = new ArrayList<>();
        for (int i=0; i<recordlist.size();i++){
            totalList.add(numericAmountList.get(i));
            totalList.add(numericGlucoseList.get(i));
        }

        model.addAttribute("numericAmountList", numericAmountList);
        model.addAttribute("numericRecordList", numericGlucoseList);
        return "board2";
    }

    @GetMapping("/board3/{memberId}")
    public String board3(Model model, @PathVariable Long memberId) {
        model.addAttribute("memberId",memberId);
        List<Record> recordlist = recordService.findRecordByMemberId(memberId); //Record
        List<Integer> amountList = new ArrayList<>(); // Amount
        List<Integer> glucoseList = new ArrayList<>(); // Glucose

        List<Integer> numericAmountList = new ArrayList<>(); // model 로 넘어가는 리스트 (amount)
        List<Integer> numericGlucoseList = new ArrayList<>(); // model 로 넘어가는 리스트 (glucose)

        for (int i=0; i<recordlist.size();i++){
            int amountForList = recordlist.get(i).getAmount();
            amountList.add(amountForList);
        }

        for (int i=0; i<recordlist.size();i++){
            int glucoseForList = recordlist.get(i).getGlucose();
            glucoseList.add(glucoseForList);
        }


        for (int i = 168; i < Math.min(252, amountList.size()); i++) {
            int amount = amountList.get(i);

            try {
                numericAmountList.add(amount);
            } catch (NumberFormatException e) {
                numericAmountList.add(null);
            }
        }

        for (int i = 168; i < Math.min(252, glucoseList.size()); i++) {

            int glucose = glucoseList.get(i);

            try {
                numericGlucoseList.add(glucose);
            } catch (NumberFormatException e) {
                numericGlucoseList.add(null);
            }
        }

        List<Integer> totalList = new ArrayList<>();
        for (int i=0; i<recordlist.size();i++){
            totalList.add(numericAmountList.get(i));
            totalList.add(numericGlucoseList.get(i));
        }

        model.addAttribute("numericAmountList", numericAmountList);
        model.addAttribute("numericRecordList", numericGlucoseList);
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

