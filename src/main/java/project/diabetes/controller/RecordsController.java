package project.diabetes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.diabetes.domain.RecordsEntity;
import project.diabetes.repository.RecordsRepository;

import java.util.ArrayList;
import java.util.List;

import static project.diabetes.repository.RecordsRepository.glist;
import static project.diabetes.repository.RecordsRepository.recordlist;

@Controller
public class RecordsController {

    @Autowired //스프링 부트가 미리 생성해 놓은 객체를 가져다 자동 연결
    private RecordsRepository recordsRepository;

    @PostMapping("/record/{memberId}/save")
    public String createRecord(RecordsDto recordsDto) {
        System.out.println(recordsDto.toString());

        //1.Dto -> Entity로 변환
        RecordsEntity recordsEntity = recordsDto.toEntity();
        System.out.println(recordsEntity.toString());

        //2.Repository 에게 Entity를 DB에게 저장하게 함
        RecordsEntity saved = recordsRepository.save(recordsEntity);

        //recordlist로 amount, glucose 가져옴
        recordlist.add(String.valueOf(saved).split(",")[0]);
        recordlist.add(String.valueOf(saved).split(",")[1]);
        System.out.println(recordlist);

        //glist로 glucose만 가져옴
        glist.add(String.valueOf(saved).split(",")[1]);
        System.out.println(glist);

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
        System.out.println(glist);
        return "graph/{memberId}";
    }
}

