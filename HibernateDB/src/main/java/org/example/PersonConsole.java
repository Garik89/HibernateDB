package org.example;

import org.example.dao.PersonDAO;
import org.example.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PersonConsole {

    private static final Logger logger = LoggerFactory.getLogger(PersonDAO.class);

    public static void createConsole()
    {
        Scanner scanner = new Scanner(System.in);
        int age = -1;
        int counterAge = 0;

        System.out.println("Введите имя человека: ");
        String name = scanner.nextLine();


        while (age < 0 || age > 100) {
            if(counterAge == 0)
            {
                System.out.println("Введите возраст человека: ");
            }
            else if (age < 0 || age > 100) {
                System.out.println("Введите корректный возраст человека: ");
            }

            age = Integer.parseInt(scanner.nextLine());
            counterAge++;
        }

        System.out.println("Введите email человека: ");
        String email = scanner.nextLine();

        Person person = new Person(name, age, email, new Date());

        PersonDAO.create(person);

        logger.info("Добавлен новый человек в базу данных: " + person);
        System.out.println();
        System.out.println();

    }

    public static void readConsole()
    {

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

                    System.out.println(PersonDAO.read(id));
                    flag = false;
                    break;
                }
                case "2": {
                    List<Person> personList = PersonDAO.read("FROM Person", Person.class);

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



    }


    public static void updateConsole()
    {
        Person person = null;
        String option;
        Scanner scanner = new Scanner(System.in);
        String id = null;
        String age;
        String name;
        String email;

        int agepsevdo = -1;
        int counterAge = 0;

        int count = 0;


        while (person == null) {

            if(count == 0) {
                System.out.println("Введите id изменяемого человека:");
            }
            else
            {   logger.error("Ошибка введения данных");
                System.out.println("Пользователь с таким id не найден. Введите id изменяемого человека:");}

            System.out.println();

            id = scanner.nextLine();
            person = PersonDAO.read(id);
            count++;
        }

        System.out.println("Введите новое имя для человека:");
        System.out.println();
        name = scanner.nextLine();


        while (agepsevdo < 0 || agepsevdo > 100) {
            if(counterAge == 0)
            {
                System.out.println("Введите новый возраст человека: ");
            }
            else if (agepsevdo < 0 || agepsevdo > 100) {
                System.out.println("Введите корректный возраст человека: ");
            }

            agepsevdo = Integer.parseInt(scanner.nextLine());
            counterAge++;
        }
        age = String.valueOf(agepsevdo);

        System.out.println("Введите новый email для человека:");
        System.out.println();
        email = scanner.nextLine();

        PersonDAO.update(id, name, age, email);


        logger.info("Обновлен человек в базе данных: " + person);
        System.out.println();
        System.out.println();


    }

    public static void deleteConsole()
    {
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
            person = PersonDAO.read(id);
            count++;
        }

        PersonDAO.delete(person);
        logger.info("Удален человек из базы данных: " + person);
    }
}
