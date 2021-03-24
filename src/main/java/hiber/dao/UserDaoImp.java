package hiber.dao;

import hiber.model.Car;
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

    @Override
    public User getCarOwner(String model, int series) {
        Query<Car> carQuery = sessionFactory.getCurrentSession().createQuery("from Car where model =: car_model and series =: car_series")
                .setParameter("car_model",model)
                .setParameter("car_series",series);
        List<Car> carList = carQuery.getResultList();
        if (!carList.isEmpty()){
            Car carFind = carList.get(0);
            List<User> userList = listUsers();
            User userFind = userList.stream()
                    .filter(user -> user.getCar().equals(carFind))
                    .findAny()//возвращает первый попавшийся элемент из Stream-a, в виде обертки Optional.
                    .orElse(null);//возвращает объект по дефолту. срабатываeт в том случае, если объекта в полученном Optional не нашлось
            return userFind;
        }
        return null;
    }

}
