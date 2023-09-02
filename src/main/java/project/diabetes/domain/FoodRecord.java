package project.diabetes.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class FoodRecord{ // food_record

    @Id @GeneratedValue
    @Column(name="food_id")
    private Long id;

    private String name; // food 에서
    private int gram; //화면에서
    private float carbohydrate; // food 에서
    private float protein; // food 에서
    private float fat; // food 에서

    private LocalDateTime inputDate = LocalDateTime.now(); //자동으로
    private String category; // food 에서
    private Long member_id;

    public FoodRecord() {
    }

    public FoodRecord(String name, int gram, float carbohydrate, float protein, float fat, String category, Long member_id) {
        this.name = name;
        this.gram = gram;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.category = category;
        this.member_id = member_id;
    }
}
