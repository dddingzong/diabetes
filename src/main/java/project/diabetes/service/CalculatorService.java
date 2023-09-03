package project.diabetes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.diabetes.domain.Food;
import project.diabetes.domain.FoodRecord;
import project.diabetes.domain.Member;
import project.diabetes.repository.CalculatorRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculatorService {

    private final CalculatorRepository calculatorRepository;

    @Transactional
    public Food findByName(String name){
        return calculatorRepository.findByName(name);
    }

    @Transactional
    public void saveFoodRecord(FoodRecord foodRecord){
        calculatorRepository.saveFoodRecord(foodRecord);
    }

    @Transactional
    public List<FoodRecord> findAllFoodRecord(){
        return calculatorRepository.findAllFoodRecord();
    }

    @Transactional
    public FoodRecord findFoodRecord(long id){
        return calculatorRepository.findFoodRecord(id);
    }

    public boolean checkFood(String name){
        return calculatorRepository.checkFood(name);
    }


    public List<Integer> getProgress(Long memberId) {
        LocalDateTime nowDate = LocalDateTime.now();
        int NowYear = nowDate.getYear();
        int NowMonth = nowDate.getMonthValue();
        int NowDay = nowDate.getDayOfMonth();

        int totalCarbohydrate = 0;
        int totalProtein = 0;
        int totalFat = 0;

        List<FoodRecord> allFoodRecord = calculatorRepository.findFoodRecordByMemberId(memberId);
        for (FoodRecord foodRecord : allFoodRecord) {
            if (foodRecord.getInputDate().getYear()==NowYear && foodRecord.getInputDate().getMonthValue()==NowMonth && foodRecord.getInputDate().getDayOfMonth()==NowDay){
                totalCarbohydrate += foodRecord.getCarbohydrate();
                totalProtein += foodRecord.getProtein();
                totalFat += foodRecord.getFat();
            }
        }

        List<Integer> progressList = new ArrayList<>();
        progressList.add(totalCarbohydrate);
        progressList.add(totalProtein);
        progressList.add(totalFat);

        return progressList;
    }

    public String warning(List<String >catelist,int fatSum){
        String warning = "";
        int flag = 0;

        for (String category : catelist) {
            if(category.contains("채소")){
                flag=1;
                break;
            }
        }

        if (flag==0){
            warning += "식단에 채소가 존재하지 않습니다. 채소를 더 먹길 추천드려요.";
        } else{
            warning += "";
        }

        if(fatSum >= 40){
            warning += "\r\n지방을 너무 많이 먹게되면 혈당 밀림 현상이 발생할 수 있습니다. 주의하세요.";
        } else{
            warning += "";
        }
        return warning;
    }

    public int calculateIcr(int carbohydrateSum, int amount) {
        return carbohydrateSum/amount;
    }

    public Member findMemberByMemberId(Long memberId){
        return calculatorRepository.findMemberByMemberId(memberId);
    }

    @Transactional
    public void flush(){
        calculatorRepository.flush();
    }
}
