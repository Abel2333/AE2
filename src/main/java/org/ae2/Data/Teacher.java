package org.ae2.Data;

import java.util.HashSet;
import java.util.Vector;

public class Teacher extends Stuff {
    private boolean isTrained;
    private Vector<String> courses;
    private HashSet<String> invitations;

    public Teacher(String name, int ID, String pwd, int age, boolean isTrained) {
        super(name, ID, pwd, age);
        this.isTrained = isTrained;
    }

    public Teacher() {
        super();
        this.isTrained = false;
    }

    @Override
    public String toString() {
        return "{" + super.toString() +
                "isTrained=" + isTrained +
                '}';
    }

    public Teacher(String name, int ID, String pwd, int age) {
        super(name, ID, pwd, age);
        this.isTrained = false;
    }

    public boolean isTrained() {
        return isTrained;
    }

    public void setTrained(boolean trained) {
        isTrained = trained;
    }

    public Vector<String> getCourses() {
        return this.courses;
    }

    public void setCourse(Vector<String> courses) {
        this.courses = courses;
    }

    public HashSet<String> getInvitations() {
        return invitations;
    }

    public void setInvitations(HashSet<String> invitations) {
        this.invitations = invitations;
    }

    public void removeInvitation(String invitationID) {
        this.invitations.remove(invitationID);
    }

    public void addInvitation(String invitationID) {
        if (this.invitations == null) {
            this.invitations = new HashSet<>();
        }
        this.invitations.add(invitationID);
    }

    public void addCourse(String course) {
        if (this.courses == null) {
            this.courses = new Vector<>();
        }
        this.courses.add(course);
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Is trained: " + this.isTrained);
    }
}
