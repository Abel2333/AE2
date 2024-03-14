package org.ae2.Data;

import org.ae2.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Stuff implements Serializable {
    private String name;
    private int ID;
    private String password;
    private int age;
    private static String position;
    private String salt;
    private LocalDateTime lastLogged;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public void display() {
        System.out.println("Name: " + this.name);
        System.out.println("ID: " + this.ID);
        System.out.println("Age: " + this.age);
        System.out.println("Position: " + position);
        if (this.lastLogged != null) {
            System.out.println("Last logged: " + this.lastLogged.format(formatter));
        }
    }

    public Stuff() {
        this.name = null;
        this.ID = 0;
        this.password = null;
        this.age = 0;
        this.setPosition();
        this.salt = null;
        this.lastLogged = null;
    }

    public Stuff(String name, int ID, String password, int age) {
        this.name = name;
        this.ID = ID;
        this.password = password;
        this.age = age;
        this.salt = null;
        this.setPosition();
        this.lastLogged = null;
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // 默认反序列化
        in.defaultReadObject();
        // 在反序列化方法中手动设置静态变量的值
        this.setPosition();
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", ID=" + ID +
                ", age=" + age +
                ", position='" + position + "', ";
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void encryptPassword() {
        this.salt = Utils.generateSalt();
        this.password = Utils.encrypt(this.password, this.salt, String.valueOf(this.ID));
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

    public void setPosition() {
        String fullName = String.valueOf(this.getClass());
        int lastIndex = fullName.lastIndexOf('.');
        position = fullName.substring(lastIndex + 1);
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public LocalDateTime getLastLogged() {
        return lastLogged;
    }

    public void setLastLogged(LocalDateTime lastLogged) {
        this.lastLogged = lastLogged;
    }

    public String acquireFormatLastLogged() {
        if (this.lastLogged == null) {
            this.lastLogged = LocalDateTime.now();
        }
        return this.lastLogged.format(formatter);
    }

    public boolean login(String pwd) {
        String encryptedPwd = Utils.encrypt(pwd, this.salt, String.valueOf(this.ID));
        return encryptedPwd.equals(this.password);
    }
}

abstract class StuffFactory {
    public abstract Stuff createStuff(String name, int ID, String pwd, int age);

    public abstract Stuff createStuff();
}

