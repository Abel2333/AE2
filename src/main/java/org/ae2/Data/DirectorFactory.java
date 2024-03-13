package org.ae2.Data;

public class DirectorFactory extends StuffFactory{
    @Override
    public Director createStuff(String name, int ID, String pwd, int age) {
        return new Director(name, ID, pwd, age);
    }

    @Override
    public Director createStuff() {
        return new Director();
    }
}
