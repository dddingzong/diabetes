package project.diabetes.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
public class Result {

    @Id@GeneratedValue
    @Column(name="result_id")
    private Long id;
    private int amount; // 승환이
    private int glucose; // 이용지
    private int carbohydrateSum; // 승환이
    private Integer icr_update; //승환이
    private Integer icr; // 고정
    private int goal; // 고정
    private Long member_id;

}
