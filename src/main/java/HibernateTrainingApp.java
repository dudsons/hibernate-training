import model.Employee;
import n1problem.model.Author;
import n1problem.model.Message;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;


public class HibernateTrainingApp {

    private static SessionFactory factory;

    public static void main(String[] args) {

        try {
            factory = new Configuration().configure()
                                         .addAnnotatedClass(Employee.class)
                                         .addAnnotatedClass(Message.class)
                                         .addAnnotatedClass(Author.class)
                                         .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        addExampleDataForN1Problem();

        // HibernateTrainingApp ME = new HibernateTrainingApp();


        //     /* Add few employee records in database */
        //     Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
        //     Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
        //     Integer empID3 = ME.addEmployee("John", "Paul", 10000);
        //     Integer empID4 = ME.addEmployee("Zara", "Ali", 2000);
        //     Integer empID5 = ME.addEmployee("Daisy", "Das", 5000);
        //     Integer empID6 = ME.addEmployee("John", "Paul", 5000);
        //     Integer empID7 = ME.addEmployee("Mohd", "Yasee", 3000);
        //
        //     ME.getListOfEmployeesWithSalaryHigherThan6000();
        //     // /* List down all the employees */
        //     // ME.listEmployees();
        //     //
        //     // /* Update employee's records */
        //     // ME.updateEmployee(empID1, 5000);
        //     //
        //     // /* Delete an employee from the database */
        //     // ME.deleteEmployee(empID2);
        //     //
        //     // /* List down new list of the employees */
        //     // ME.listEmployees();
        // }
        //
        // /* Method to CREATE an employee in the database */
        // public Integer addEmployee(String fname, String lname, int salary) {
        //     Session session = factory.openSession();
        //     Transaction tx = null;
        //     Integer employeeID = null;
        //
        //     try {
        //         tx = session.beginTransaction();
        //         Employee employee = new Employee();
        //         employee.setFirstName(fname);
        //         employee.setLastName(lname);
        //         employee.setSalary(salary);
        //         employeeID = (Integer) session.save(employee);
        //         tx.commit();
        //     } catch (HibernateException e) {
        //         if (tx != null) tx.rollback();
        //         e.printStackTrace();
        //     } finally {
        //         session.close();
        //     }
        //     return employeeID;
    }

    /* Method to  READ all the employees */
    public void listEmployees() {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext(); ) {
                Employee employee = (Employee) iterator.next();
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print("  Last Name: " + employee.getLastName());
                System.out.println("  Salary: " + employee.getSalary());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to UPDATE salary for an employee */
    public void updateEmployee(Integer EmployeeID, int salary) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
            employee.setSalary(salary);
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from the records */
    public void deleteEmployee(Integer EmployeeID) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void getListOfEmployeesWithSalaryHigherThan6000() {
        Session session = factory.openSession();
        String query = "FROM Employee E WHERE E.salary >6000";
        try {
            Transaction transaction = session.beginTransaction();
            Query query1 = session.createQuery(query);
            List<Employee> employees = (List<Employee>) query1.getResultList();
            for (Employee employee : employees) {
                System.out.println(employee);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void addExampleDataForN1Problem () {
        Session session = factory.openSession();

        Author author1 = new Author();
        author1.setName("Marek");

        Author author2 = new Author();
        author2.setName("Anna");

        Message message1 = new Message();
        message1.setText("Siała baba mak");
        message1.setAuthor(author1);

        Message message2 = new Message();
        message2.setText("Nie wiedziała jak");
        message2.setAuthor(author1);

        Message message3 = new Message();
        message3.setText("A dziad wiediał");
        message3.setAuthor(author1);

        Message message4 = new Message();
        message4.setText("Hej co porabiasz");
        message4.setAuthor(author2);

        try {
            Transaction transaction = session.beginTransaction();
            session.save(author1);
            session.save(author2);
            session.save(message1);
            session.save(message2);
            session.save(message3);
            session.save(message4);

            transaction.commit();
        } catch (Exception e) {
            throw new HibernateException(e);
        } finally {
            session.close();
        }

    }

}

