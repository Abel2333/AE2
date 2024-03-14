package org.ae2.Data;

public abstract class StuffFactory {
    public abstract Stuff createStuff(String name, int ID, String pwd, int age);

    public abstract Stuff createStuff();
}
