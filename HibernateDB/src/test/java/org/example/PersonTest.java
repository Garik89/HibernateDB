package org.example;

import org.example.dao.PersonDAO;
import org.example.model.Person;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonTest {

    private static PostgreSQLContainer<?> postgresContainer;

    private SessionFactory sessionFactory;
    private PersonDAO personDAO;
    private PersonConsole personConsole;

    @BeforeAll
    public void startContainer() {
        postgresContainer = new PostgreSQLContainer<>("postgres:17")
                .withDatabaseName("testdb")
                .withUsername("user")
                .withPassword("password");
        postgresContainer.start();
    }

    @AfterAll
    public void stopContainer() {
        if (postgresContainer != null) {
            postgresContainer.stop();
        }
    }

    @BeforeEach
    public void setup() {
        Configuration configuration = new Configuration();

        // Подключение к базе внутри контейнера
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", postgresContainer.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgresContainer.getUsername());
        configuration.setProperty("hibernate.connection.password", postgresContainer.getPassword());

        // Dialect PostgreSQL
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        // Создавать/обновлять структуру таблиц автоматически при запуске
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");

        // Показывать SQL в консоли (необязательно)
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");

//        // Регистрируем entity-класс
//          personDAO = new PersonDAO();

    }

    @AfterEach
    public void cleanup() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }


    @Test
    public void testSaveAndFindUser() {
        Person person = new Person();

        person.setName("Дон");
        person.setAge(44);
        person.setEmail("fddd@mail.ru");

        PersonDAO.create(person);

        assertNotNull(person.getId());

        Person retrieved = PersonDAO.read(Integer.toString(person.getId()));
        assertNotNull(retrieved);
        assertEquals(person, retrieved);

    }

}
