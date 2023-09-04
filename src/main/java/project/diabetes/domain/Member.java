package project.diabetes.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private long id;
    private String pw;
    private String name;
    private int age;
    private String sex;
    private Integer icr;
    private float height;
    private float weight;
    private Integer goal;

    private String userId;
}
