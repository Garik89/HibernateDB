package org.example.dao;

import org.example.PersonSession;
import org.example.model.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PersonDAO {


    private static final Logger logger = LoggerFactory.getLogger(PersonDAO.class);
    //Метод добавления человека в базу данных
    public static void create(Person person)
    {
        PersonSession personSession = new PersonSession(Person.class);
        Session session = personSession.getSession();


        try {
            session.beginTransaction();

            session.persist(person);

            session.getTransaction().commit();

        }

        catch (HibernateException e) {
            System.out.println();
            System.out.println();
            logger.error("Ошибка введения данных");
            System.out.println("Введено недопустимое имя или email значение");
            System.out.println();
            System.out.println();
        }

        finally{
        personSession.getSessionFactory().close();
          }

    }

    //Перегрузка метода считывания одного человека из базы
    public static Person read(String id) {

        PersonSession personSession = new PersonSession(Person.class);
        Person person;

        try {
            personSession.getSession().beginTransaction();

            person = personSession.getSession().find(Person.class, Integer.parseInt(id));

            personSession.getSession().getTransaction().commit();

        }
        finally{
                personSession.getSessionFactory().close();
            }

        return person;
    }

    //Перегрузка метода считывания всех людей из базы
    public static List<Person> read(String query, Class clas) {

        PersonSession personSession = new PersonSession(Person.class);
        List<Person> persons;

        try {
            personSession.getSession().beginTransaction();

            persons = personSession.getSession().createQuery(query, clas).getResultList();

            personSession.getSession().getTransaction().commit();

        }
        finally{
            personSession.getSessionFactory().close();
        }

        return persons;
    }

    //Метод обновления человека в базе
    public  static void update(String id, String name, String age, String email)
    {
        PersonSession personSession = new PersonSession(Person.class);
        Person person;

        try {
            personSession.getSession().beginTransaction();
            person = personSession.getSession().find(Person.class, Integer.parseInt(id));

            person.setName(name);
            person.setAge(Integer.parseInt(age));
            person.setEmail(email);
            person.setCreated_at(new Date());

            personSession.getSession().getTransaction().commit();

        }
        catch (HibernateException e) {
            logger.error("Ошибка введения данных");
            System.out.println("Введено недопустимое имя или email значение");
        }

        finally{
            personSession.sessionFactory.close();
        }

        }


    //Метод удаления человека из базы
    public static void delete(Person person)
    {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.remove(person);
            session.getTransaction().commit();


        }

        catch (HibernateException e) {
            System.out.println("Ошибка при работе с базой данных");
        }

        finally {
            sessionFactory.close();
        }

    }

    }


