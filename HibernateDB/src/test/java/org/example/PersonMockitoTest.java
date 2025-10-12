package org.example;

import org.example.PersonConsole;
import org.example.dao.PersonDAO;
import org.example.model.Person;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class) // для интеграции Mockito и JUnit 5
public class PersonMockitoTest {

    @Test
    void testCreateConsole() {
        // Подготавливаем ввод: имя, возраст, email (по порядку)
        String input = "John Doe\n25\njohn.doe@example.com\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Статический мок PersonDAO
        try (MockedStatic<PersonDAO> mockedPersonDAO = Mockito.mockStatic(PersonDAO.class)) {
            // Ничего не настраиваем в create, просто хотим проверить вызов

            // Вызываем тестируемый метод
            PersonConsole.createConsole();

            // Проверяем, что PersonDAO.create вызвался ровно 1 раз с объектом Person
            mockedPersonDAO.verify(() -> PersonDAO.create(any(Person.class)), times(1));

            // Можно дополнительно проверить, что объект Person имеет имя "John Doe" и возраст 25
            // Для этого перехватываем аргумент
            mockedPersonDAO.verify(() -> PersonDAO.create(Mockito.argThat(person ->
                    person.getName().equals("John Doe")
                            && person.getAge() == 25
                            && person.getEmail().equals("john.doe@example.com"))));
        }
    }
}