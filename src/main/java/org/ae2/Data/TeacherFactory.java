package org.ae2.Data;

public class TeacherFactory extends StuffFactory {
    @Override
    public Teacher createStuff(String name, int ID, String pwd, int age) {
        return new Teacher(name, ID, pwd, age);
    }
    @Override
    public Teacher createStuff(){
        return new Teacher();
    }
}
