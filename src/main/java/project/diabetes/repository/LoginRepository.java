package project.diabetes.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.diabetes.domain.Login;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class LoginRepository {

    private final EntityManager em;

    public void signUp(Login login){
        em.persist(login);
    }

    // 아이디 중복을 확인하기 위해 아이디로 DB검색 로직
    public boolean checkDuplicateId(String userId){
        try {
            em.createQuery("select l from Login l where l.userId=:userId", Login.class)
                .setParameter("userId", userId)
                .getSingleResult();
            return true; // 아이디 중복
        } catch (Exception e){
            return false; //아이디 미중복
        }
    }

    public boolean checkNoUserId(String login_userId) {
        try {
            em.createQuery("select l from Login l where l.userId=:user_id", Login.class)
                    .setParameter("user_id", login_userId)
                    .getSingleResult();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public Login Login(String login_userId){
        return em.createQuery("select l from Login l where l.userId=:user_id",Login.class)
                .setParameter("user_id",login_userId)
                .getSingleResult();
    }
}
