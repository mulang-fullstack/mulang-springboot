package yoonsome.mulang.mypage.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import yoonsome.mulang.user.entity.User;

@Repository
public class ModifyRepository {
    @PersistenceContext
    private EntityManager em;

    public User findById(Long id){
        return em.find(User.class, id);
    }
    public void update(User user){
    }
}
