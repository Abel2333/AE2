package org.ae2.Data;

public class TeacherFactory extends StuffFactory {
    @Override
    public Stuff createStuff(String name, int ID, String pwd, int age, String position) {
        return new Teacher(name, ID, pwd, age, position);
    }
    @Override
    public Stuff createStuff(){
        return new Teacher();
    }
}
