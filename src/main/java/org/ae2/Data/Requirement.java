package org.ae2.Data;

import java.io.Serializable;
import java.util.*;

public class Requirement implements Serializable {
    private HashSet<Integer> ageSet;
    private String course;
    private int maxAge;
    private int minAge;
    private int creatorID;
    private String ID;
    private Vector<Integer> involvedTeachers;


    public Requirement() {
        this.ageSet = new HashSet<>();
        this.minAge = -1;
        this.maxAge = -1;
        this.course = null;
        this.creatorID = -1;
        this.ID = UUID.randomUUID().toString();
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public HashSet<Integer> getAgeSet() {
        return ageSet;
    }

    public void setAgeSet(HashSet<Integer> ageSet) {
        this.ageSet = ageSet;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Vector<Integer> getInvolvedTeachers() {
        return involvedTeachers;
    }

    public void setInvolvedTeachers(Vector<Integer> involvedTeachers) {
        this.involvedTeachers = involvedTeachers;
    }

    public void addTeacher(int teacherID) {
        if (this.involvedTeachers == null) {
            this.involvedTeachers = new Vector<>();
        }
        this.involvedTeachers.add(teacherID);
    }

    public boolean isSatisfy(int age, Vector<String> teacherCourses) {
        if ((this.course != null) && (!teacherCourses.contains(this.course))) {
            return false;
        }
        if ((this.minAge < this.maxAge) && ((age < this.minAge) || (age > this.maxAge))) {
            return false;
        }
        return this.ageSet.contains(age);
    }

    public void display() {
        System.out.println(this.ID);
        if (this.minAge < this.maxAge) {
            System.out.println("age: " + this.minAge + " - " + this.maxAge);
        } else if ((this.maxAge == this.minAge) && (this.maxAge > 0)) {
            System.out.println("age: " + this.maxAge);
        } else if (!this.ageSet.isEmpty()) {
            System.out.println("age: " + this.ageSet);
        }

        if (this.course != null) {
            System.out.println("Course: " + this.course);
        }

        if (this.involvedTeachers != null) {
            System.out.println("The following teachers involved this requirement: ");
            for (Integer id : this.involvedTeachers) {
                System.out.println(Objects.requireNonNull(GlobalData.getStuff(id)).getName());
            }
        }

        System.out.println("Creator: " + Objects.requireNonNull(GlobalData.getStuff(this.creatorID)).getName());
        System.out.println("\n");
    }

    @Override
    public String toString() {
        return "Requirements{" +
                "age=" + ageSet +
                ", course='" + course + '\'' +
                ", maxAge=" + maxAge +
                ", minAge=" + minAge +
                ", creatorID=" + creatorID +
                '}';
    }
}
