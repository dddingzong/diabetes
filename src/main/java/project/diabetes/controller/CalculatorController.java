package project.diabetes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.diabetes.domain.Food;
import project.diabetes.domain.FoodRecord;
import project.diabetes.domain.Member;
import project.diabetes.service.CalculatorService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    @GetMapping("/calculator/{memberId}")  //인슐린 계산 페이지
    public String calculator(Model model, @PathVariable Long memberId){
        Member member = calculatorService.findMemberByMemberId(memberId);
        model.addAttribute("memberId",memberId);

        if (member.getIcr() == null){
            return "calculatorTest";
        }

        // 게이지값 동시에 갱신 !! (inputDate 를 기준으로 같은날 뽑아오기)
        List<Integer> progressList= calculatorService.getProgress(memberId);
        int progress_Carbohydrate = progressList.get(0);
        int progress_Protein = progressList.get(1);
        int progress_Fat = progressList.get(2);
        model.addAttribute("progress_Carbohydrate",progress_Carbohydrate);
        model.addAttribute("progress_Protein",progress_Protein);
        model.addAttribute("progress_Fat",progress_Fat);

        return "calculator";
    }

    @PostMapping("/calculator/{memberId}")
    public String calculate(Model model, String meal, @ModelAttribute(value = "FoodFormListDto") FoodFormListDto foodlist, @PathVariable Long memberId) {
        model.addAttribute("memberId",memberId);
        //사이트에서 name 이랑 g 가져오기 (여러개임)
        List<String> namelist = new ArrayList<>();
        List<Integer> gramlist = new ArrayList<>();
        // 경고문을 위한 list
        List<String> catelist = new ArrayList<>();
        // 걍고문을 위한 수치
        int fatSum = 0;

        // api 정리
        int carbohydrateSum=0;
        Long member_id = 0L;

        for (int i=0;i<foodlist.getFoodlist().size();i++){
            namelist.add(foodlist.getFoodlist().get(i).getName());
            gramlist.add(foodlist.getFoodlist().get(i).getGram());
        }
        // namelist, gramlist 분리 성공!!


        // food 가 db 에 존재하는지 (1차 테스트)
        for (int i=0;i<namelist.size();i++) {
            if (!(calculatorService.checkFood(namelist.get(i)))) {
                model.addAttribute("DbWarning", "데이터베이스에 존재하지 않는 음식입니다. 확인 후 다시 입력해주세요");

                // 게이지값 동시에 갱신 !! (inputDate 를 기준으로 같은날 뽑아오기)
                List<Integer> progressList= calculatorService.getProgress(memberId);
                int progress_Carbohydrate = progressList.get(0);
                int progress_Protein = progressList.get(1);
                int progress_Fat = progressList.get(2);
                model.addAttribute("progress_Carbohydrate",progress_Carbohydrate);
                model.addAttribute("progress_Protein",progress_Protein);
                model.addAttribute("progress_Fat",progress_Fat);

                return "/calculator";
            }
        }
        // 모든 gram 이 int 인지 (2차 테스트)


        for (int i = 0;i<namelist.size();i++) {
            // food_db 에서 name 별 carbohydrate, protein, fat, category 추출
            Food food = calculatorService.findByName(namelist.get(i));

            float food_carbohydrate = food.getCarbohydrate(); //100 그램당 탄수화물
            float food_protein = food.getProtein(); //100 그램당 단백질
            float food_fat = food.getFat(); //100 그랜당 지방
            String food_category = food.getCategory();
            catelist.add(food_category);

            String food_name = food.getName();
            int food_gram = gramlist.get(i);

            // gram 에 맞춰서 탄단지 계산
            float real_carbohydrate = (food_carbohydrate / 100) * food_gram;
            float real_protein = (food_protein / 100) * food_gram;
            float real_fat = (food_fat / 100) * food_gram;

            fatSum += real_fat;
            carbohydrateSum += real_carbohydrate;
            // food_record_db 에 다 넣기
            // (id, name, gram, carbohydrate, protein, fat, inputDate, category, memberId)
            FoodRecord foodRecord = new FoodRecord(food_name, food_gram, real_carbohydrate, real_protein, real_fat, food_category, memberId);
            calculatorService.saveFoodRecord(foodRecord);
        }

        // 게이지값 동시에 갱신 !! (inputDate 를 기준으로 같은날 뽑아오기)
        List<Integer> progressList= calculatorService.getProgress(memberId);
        int progress_Carbohydrate = progressList.get(0);
        int progress_Protein = progressList.get(1);
        int progress_Fat = progressList.get(2);
        model.addAttribute("progress_Carbohydrate",progress_Carbohydrate);
        model.addAttribute("progress_Protein",progress_Protein);
        model.addAttribute("progress_Fat",progress_Fat);

        // 경고문 갱신!! (category 로?)
        // catelist 에 채소가 없으면 경고문 + fatSum 의 값이 40 넘으면 경고문
        String warning = calculatorService.warning(catelist, fatSum);
        model.addAttribute("warning",warning);

        // api 값 보내주기
        // 승환이 한테 보내야하는 값: 탄수합(carbohydrateSum), 식사 여부(snack), memberId
        int snack;

        if (meal.equals("식사")){ // 식사면 Y 간식이면 N // 식사면 0 간식이면 1
            snack = 0;
        } else {
            snack = 1;
        }
//        System.out.println("carbohydrateSum = " + carbohydrateSum); // 수정해야됨
//        System.out.println("snack = " + snack);
//        System.out.println("memberId = " + memberId);
        model.addAttribute("DbWarning", "정확한 결과를 위해 혈당 등록을 통하여 혈당 일지를 작성해주세요.");
        return "/calculator";
    }

    @GetMapping("/calculatorTest/{memberId}")
    public String calculateTestGet(Model model, @PathVariable Long memberId){
        model.addAttribute("memberId",memberId);
        model.addAttribute("DbWarning", "데이터베이스에 존재하지 않는 음식입니다. 확인 후 다시 입력해주세요");
        return "calculatorTest";
    }

    @GetMapping("/calculatorTest/{memberId}/reTest")
    public String calculateRetestGet(Model model, @PathVariable Long memberId){
        model.addAttribute("memberId",memberId);
        model.addAttribute("DbWarning",
                "식전 혈당과 식후 혈당의 차이가 너무 큽니다. 인슐린 투여량을 조절 후 다음 식사시간에 다시 테스트 해주십시오");
        return "calculatorTest";
    }


    @PostMapping("/calculatorTest/{memberId}")
    public String calculateTest(Model model, @ModelAttribute(value = "FoodFormListDto") FoodFormListDto foodlist, int amount, int glucose, int bglucose, @PathVariable Long memberId) {
        model.addAttribute("memberId",memberId);
        //사이트에서 name 이랑 g 가져오기 (여러개임)
        List<String> namelist = new ArrayList<>();
        List<Integer> gramlist = new ArrayList<>();

        for (int i=0;i<foodlist.getFoodlist().size();i++){
            namelist.add(foodlist.getFoodlist().get(i).getName());
            gramlist.add(foodlist.getFoodlist().get(i).getGram());
        }
        // namelist, gramlist 분리 성공!!

        int carbohydrateSum = 0;

        // food 가 db 에 존재하는지 (1차 테스트)
        for (int i =0;i<namelist.size();i++) {
            if (!(calculatorService.checkFood(namelist.get(i)))) {
                return "redirect:/calculatorTest/"+memberId;
            }
        }

        // 식전혈당과 식후혈당의 차이가 클 때 (재테스트)
        if (((glucose - bglucose)>80)||(glucose-bglucose)<0){
            return "redirect:/calculatorTest/"+memberId+"/reTest";
        }

        for (int i = 0;i<namelist.size();i++) {
            // food_db 에서 name 별 carbohydrate 추출
            Food food = calculatorService.findByName(namelist.get(i));

            float food_carbohydrate = food.getCarbohydrate(); //100 그램당 탄수화물
            int food_gram = gramlist.get(i);

            // gram 에 맞춰서 탄수화물 계산
            float real_carbohydrate = (food_carbohydrate / 100) * food_gram;
            carbohydrateSum += real_carbohydrate;
        }

//        System.out.println("carbohydrateSum = " + carbohydrateSum);
//        System.out.println("bglucose = " + bglucose);
//        System.out.println("amount = " + amount);
//        System.out.println("glucose = " + glucose);

        int icr = calculatorService.calculateIcr(carbohydrateSum,amount);
        // 이거 member 에 다시 넣어야함
        Member member = calculatorService.findMemberByMemberId(memberId);
        //db에 직접 넣기로 바꿔야할듯
        member.setIcr(icr);
        calculatorService.flush();

        int goal = member.getGoal();
        calculatorService.saveFirstResult(icr,memberId,goal,glucose);

        String originalUrl = "redirect:/info/";
        return originalUrl+memberId;
    }


    @GetMapping("/test") //연관 검색어 테스트
    public String test(){
        return "test";
    }

}
