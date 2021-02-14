package n1problem.one_to_many;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OneToManyApp {

    public static SessionFactory sessionFactory;

    public static void main(String[] args) {

        //Creating session factory
        try {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Address.class)
                    .buildSessionFactory();

        } catch (Exception e) {
            throw new HibernateException(e);
        }

        addSampleDateToDb();
        getAllUsers();
        // getAllAddresses();
    }

    private static void addSampleDateToDb() {
        //Creating samples
        Address address1 = new Address();
        address1.setStreet("Gotham st");

        Address address2 = new Address();
        address2.setStreet("Cave st");

        Set<Address> addressSet = new HashSet<>();
        addressSet.add(address1);
        addressSet.add(address2);

        User user1 = new User();
        user1.setName("Batman");
        user1.setAddresses(addressSet);

        User user2 = new User();
        user2.setName("CatWoman");
        user2.setAddresses(addressSet);

        User user3 = new User();
        user3.setName("Spiderman");
        user3.setAddresses(addressSet);

        //Adding data to db
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user1);
            session.save(user2);
            session.save(user3);
            session.save(address1);
            session.save(address2);
            transaction.commit();
        } catch (Exception e) {
            throw new HibernateException(e);
        } finally {
            session.close();
        }
    }

    private static void getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            String query = "FROM User";
            List<User> users = session.createQuery(query).list();

            // for (User user: users) {
            //     System.out.println(user.getAddresses());
            // }

            transaction.commit();
        } catch (Exception e) {
            throw new HibernateException(e);
        }finally {
            session.close();
        }
    }

    private static void getAllAddresses() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            String query = "FROM Address";
            List<Address> addressList = session.createQuery(query).list();

            transaction.commit();
        } catch (Exception e) {
            throw new HibernateException(e);
        }finally {
            session.close();
        }
    }
}
