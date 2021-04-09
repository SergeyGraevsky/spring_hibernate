package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    // Упростил метод getCarOwner
    @Override
    public User getCarOwner(String model, int series) {
        Query<User> carQuery = sessionFactory.getCurrentSession().createQuery("select user from Car where model =: car_model and series =: car_series")
                .setParameter("car_model", model)
                .setParameter("car_series", series);
        return carQuery.getSingleResult();
    }
}
