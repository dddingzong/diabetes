package project.diabetes.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.diabetes.domain.Food;
import project.diabetes.domain.FoodRecord;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor //자동 의존성 주입 (없으면 에러남)
public class CalculatorRepository{

    private final EntityManager em;

    public Food findByName(String name){
        return em.createQuery("select f from Food f where f.name=:name",Food.class)
                .setParameter("name",name)
                .getSingleResult();
    }

    public void saveFoodRecord(FoodRecord foodRecord){
        em.persist(foodRecord);
    }

    //같은 날짜의 모든 음식 가져와서 탄단지 합 구하기
    public List<FoodRecord> findAllFoodRecord(){
        return em.createQuery("select r from FoodRecord r").getResultList();
    }

    //test
    public FoodRecord findFoodRecord(long id){
        return em.find(FoodRecord.class,id);
    }

}
