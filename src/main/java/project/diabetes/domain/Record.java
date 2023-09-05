package project.diabetes.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Getter
@Setter
@ToString
@Table(name = "record")
public class Record {

    @Id @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    private Integer amount;
    private Integer glucose;
    private int carbohydrateSum;
    private Long member_id;

    public Record() {
    }

    public Record(Integer amount, Integer glucose, int carbohydrateSum, Long member_id) {
        this.amount = amount;
        this.glucose = glucose;
        this.carbohydrateSum = carbohydrateSum;
        this.member_id = member_id;
    }
}