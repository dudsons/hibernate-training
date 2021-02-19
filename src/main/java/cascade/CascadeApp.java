package cascade;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CascadeApp {
    private static Logger logger =  LoggerFactory.getLogger(CascadeApp.class);
    private static SessionFactory sessionFactory = null;


    public static void main(String[] args) {
        try {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Phone.class)
                    .buildSessionFactory();

            addExampleData();
            // removeAllEmployeesUsingHqlQuery();
            removeAllEmployeesUsingSessionDelete();
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


            long identifier1 = (long)session.save(employee1);
            long identifier2 = (long)session.save(employee2);
            logger.info("identifier of employee1: " + identifier1 );
            logger.info("identifier of employee2: " + identifier2 );
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

    private static void removeAllEmployeesUsingSessionDelete (){
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            String hqlQuery = "From Employee";
            List<Employee> employeeList = session.createQuery(hqlQuery).list();

            for (Employee employee: employeeList) {
                session.delete(employee);
            }
            transaction.commit();
        }catch(Exception e ){
            logger.info("Problem in removeAllEmployeesUsingSessionDelete");
            throw new HibernateException(e);
        }finally {
            session.close();
        }
    }

}
