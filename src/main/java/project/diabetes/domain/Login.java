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
public class Login {

    @Id @GeneratedValue
    @Column(name = "login_id")
    private long id;

    private String userId;
    private String userEmail;
    private String userPassword;

    public Login() {
    }

    public Login(String userId, String userEmail, String userPassword) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
}
