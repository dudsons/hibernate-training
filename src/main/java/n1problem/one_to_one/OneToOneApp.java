package n1problem.one_to_one;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class OneToOneApp {

    static SessionFactory sessionFactory = new Configuration()
            .configure()
            .addAnnotatedClass(Hero.class)
            .addAnnotatedClass(HeroDetails.class)
            .buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            //Creating sample data
            //HeroDetails
            HeroDetails heroDetails1 = new HeroDetails();
            heroDetails1.setSkill("Special web");
            HeroDetails heroDetails2 = new HeroDetails();
            heroDetails2.setSkill("Batarang");

            //Heroes
            Hero hero1 = new Hero();
            hero1.setName("Spiderman");
            hero1.setHeroDetails(heroDetails1);
            Hero hero2 = new Hero();
            hero2.setName("Batman");
            hero2.setHeroDetails(heroDetails2);

            //Sending data to db
            session.save(hero1);
            session.save(hero2);

            session.save(heroDetails1);
            session.save(heroDetails2);

            transaction.commit();

            //Get test transation
            getHeroes();

        } catch (Exception e) {
            throw new HibernateException(e);
        } finally {
            session.close();
        }
    }

    private static List<Hero> getHeroes () {

        Session session = null;
        List<Hero> heroes = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            String query = "FROM Hero";
            heroes = session.createQuery(query).list();

            // for (Hero hero: heroes){
            //     System.out.println(hero.getHeroDetails());
            // }


            transaction.commit();
        }catch (Exception e ){
            throw new HibernateException(e);
        }finally {
            session.close();
        }
        return heroes;
    }
}
