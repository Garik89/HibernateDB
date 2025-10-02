package org.example.dao;

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
    public static void create()
    {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();


        try {
            session.beginTransaction();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Введите имя человека: ");
            String name = scanner.nextLine();

            System.out.println("Введите возраст человека: ");
            int age = Integer.parseInt(scanner.nextLine());


            System.out.println("Введите email человека: ");
            String email = scanner.nextLine();

           Person person = new Person(name, age, email, new Date());

            logger.info("Добавлен новый человек в базу данных: " + person);
            System.out.println();
            System.out.println();
            session.persist(person);


            session.getTransaction().commit();

        }

        catch (HibernateException e) {
            logger.error("Ошибка введения данных");
            System.out.println("Введено недопустимое имя или email значение");
        }

        finally{
        sessionFactory.close();
          }

    }

    //Метод считывания людей из базы
    public static void read() {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            System.out.println("Введите способ чтения: 1 - человека по id, 2 - всех людей из базы");
            System.out.println();
            boolean flag = true;


            while (flag) {
                Scanner scanner = new Scanner(System.in);
                String option = scanner.nextLine();


                switch (option) {
                    case "1":
                    {
                        System.out.println("Введите id человека:");
                        System.out.println();
                        String id = scanner.nextLine();

                        System.out.println(session.find(Person.class, Integer.parseInt(id)));
                        flag = false;
                        break;
                    }
                    case "2": {
                        List<Person> personList = session.createQuery("FROM Person", Person.class).getResultList();

                        for (Person person : personList) {
                            System.out.println(person);
                        }
                        flag = false;
                        break;
                    }

                    default: {
                        logger.error("Ошибка введения данных");
                        System.out.println("Значение указано некорректно. Введите снова: ");

                    }


                }
            }

            session.getTransaction().commit();

        }
        finally{
                sessionFactory.close();
            }

    }

    //Метод обновления человека в базе
    public  static void update()
    {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Person person = null;
            String option;
            Scanner scanner = new Scanner(System.in);

            int count = 0;


            while (person == null) {

                if(count == 0) {
                    System.out.println("Введите id изменяемого человека:");
                }
                else
                {   logger.error("Ошибка введения данных");
                    System.out.println("Пользователь с таким id не найден. Введите id изменяемого человека:");}

                System.out.println();

                option = scanner.nextLine();
                person = session.find(Person.class, Integer.parseInt(option));
                count++;
            }

                System.out.println("Введите новое имя для человека:");
                System.out.println();
                option = scanner.nextLine();
                person.setName(option);

                System.out.println("Введите новый возраст для человека:");
                System.out.println();
                option = scanner.nextLine();
                person.setAge(Integer.parseInt(option));

            System.out.println("Введите новый email для человека:");
            System.out.println();
            option = scanner.nextLine();
            person.setEmail(option);

            person.setCreated_at(new Date());

            logger.info("Обновлен человек в базе данных: " + person);
            System.out.println();
            System.out.println();

            session.getTransaction().commit();

        }
        catch (HibernateException e) {
            logger.error("Ошибка введения данных");
            System.out.println("Введено недопустимое имя или email значение");
        }

        finally{
            sessionFactory.close();
        }

        }


    //Метод удаления человека из базы
    public static void delete()
    {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Scanner scanner = new Scanner(System.in);
            String id;
            Person person = null;
            int count = 0;

            while (person == null) {

                if(count == 0) {
                    System.out.println("Введите id удаляемого человека:");
                }
                else System.out.println("Пользователь с таким id не найден. Введите id удаляемого человека:");

                System.out.println();

                id = scanner.nextLine();
                person = session.find(Person.class, Integer.parseInt(id));
                count++;
            }

            session.remove(person);
            logger.info("Удален человек из базы данных: " + person);

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


