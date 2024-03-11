package org.ae2.Data;

import java.util.Objects;

public abstract class Stuff {
    private String name;
    private int ID;
    private String password;
    private int age;
    private String position;

    public Stuff(String name, int ID, String password, int age, String position) {
        this.name = name;
        this.ID = ID;
        this.password = password;
        this.age = age;
        this.position = position;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean login(String pwd){
        return Objects.equals(pwd, this.password);
    }
}

abstract class StuffFactory {
    public abstract Stuff createStuff(String name, int ID, String pwd, int age, String position);
}

