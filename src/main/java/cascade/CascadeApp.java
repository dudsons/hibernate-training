package cascade;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.Set;

public class CascadeApp {

    private static SessionFactory sessionFactory = null;


    public static void main(String[] args) {
        try {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Phone.class)
                    .buildSessionFactory();

            addExampleData();
            removeAllEmployeesUsingHqlQuery();
        } catch (Exception e) {
            throw new HibernateException("Problem with loading sessionFactory" + e);
        }

    }

    private static void addExampleData() {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            //Create example objects
            Phone phone1 = new Phone();
            phone1.setNumber(6969);
            Phone phone2 = new Phone();
            phone2.setNumber(1111);
            Set<Phone> phones1 = new HashSet<>();
            phones1.add(phone1);
            Set<Phone> phones2 = new HashSet<>();
            phones2.add(phone2);

            Employee employee1 = new Employee();
            employee1.setName("Marek");
            employee1.setPhone(phones1);
            Employee employee2 = new Employee();
            employee2.setName("Jan");
            employee2.setPhone(phones2);


            session.save(employee1);
            session.save(employee2);
            session.save(phone1);
            session.save(phone2);

            transaction.commit();

        } catch (Exception e) {
            throw new HibernateException(e);
        } finally {
            session.close();
        }
    }

    private static void removeAllEmployeesUsingHqlQuery (){
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            String hqlQuery = "DELETE from Employee";
            int resultAfterDelete = session.createQuery(hqlQuery).executeUpdate();
            System.out.println("Int after delete is: " + resultAfterDelete);
            transaction.commit();
        }catch(Exception e ){
            throw new HibernateException(e);
        }finally {
            session.close();
        }
    }
}
