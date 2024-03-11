package org.ae2;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ae2.Data.DatabaseJSON;
import org.ae2.Data.Teacher;
import org.ae2.Data.TeacherFactory;

import java.io.FileNotFoundException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException, JsonProcessingException {
        Teacher[] teachers = new Teacher[2];
        TeacherFactory teacherFactory = new TeacherFactory();
        teachers[0] = (Teacher) teacherFactory.createStuff("testStuff", 123, "112233",21, "testPosition");
        teachers[1] = (Teacher) teacherFactory.createStuff("testStuff1", 1234, "1122334",22, "testPosition1");
        DatabaseJSON databaseJSON = new DatabaseJSON("Teacher.json");
        databaseJSON.writeData(teachers);
    }
}