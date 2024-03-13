package org.ae2.Data;

public class AdministratorFactory extends StuffFactory{
    @Override
    public Administrator createStuff(String name, int ID, String pwd, int age) {
        return new Administrator(name, ID, pwd, age);
    }

    @Override
    public Administrator createStuff() {
        return new Administrator();
    }
}
