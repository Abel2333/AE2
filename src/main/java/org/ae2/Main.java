package org.ae2;

import org.ae2.Data.*;

import java.io.IOException;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        /*Teacher[] teachers = new Teacher[2];
        TeacherFactory teacherFactory = new TeacherFactory();
        teachers[0] = (Teacher) teacherFactory.createStuff("testStuff", 123, "112233", 21, "testPosition");
        teachers[1] = (Teacher) teacherFactory.createStuff("testStuff1", 1234, "1122334", 22, "testPosition1");
        DatabaseJSON databaseJSON = new DatabaseJSON(DataType.TEACHER);

        System.out.println(teachers.getClass());
        System.out.println(teachers[0].getClass());
        databaseJSON.writeData(teachers);

        System.out.println(databaseJSON.readData());*/

        GlobalStuff.initialize();
        Stuff stuff = GlobalStuff.getStuff(123);
        System.out.println(stuff);
    }
}