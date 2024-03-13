package org.ae2.Data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.ae2.Utils;

import java.nio.file.FileSystemNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class Stuff {
    private String name;
    private int ID;
    private String password;
    private int age;
    @JsonIgnore
    private String position;
    private String salt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "BST")
    private LocalDateTime lastLogged;
    @JsonIgnore
    private final DateTimeFormatter formatter;

    public void display() {
        System.out.println("Name: " + this.name);
        System.out.println("ID: " + this.ID);
        System.out.println("Age: " + this.age);
        System.out.println("Position: " + this.position);
        if (this.lastLogged!=null) {
            System.out.println("Last logged: " + this.lastLogged.format(this.formatter));
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
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }

    public Stuff(String name, int ID, String password, int age) {
        this.name = name;
        this.ID = ID;
        this.password = password;
        this.age = age;
        this.salt = null;
        this.setPosition();
        this.lastLogged = null;
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
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
        this.position = fullName.substring(lastIndex + 1);
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

    public String acquireFormatLastLogged(){
        if (this.lastLogged==null){
            this.lastLogged = LocalDateTime.now();
        }
        return this.lastLogged.format(this.formatter);
    }

    public boolean login(char[] pwd) {
        return this.login(new String(pwd));
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

