package org.ae2.Data;

public class Teacher extends Stuff {
    private boolean isTrained;

    public Teacher(String name, int ID, String pwd, int age, String position, boolean isTrained) {
        super(name, ID, pwd, age, position);
        this.isTrained = isTrained;
    }

    public Teacher() {
        super();
    }

    @Override
    public String toString() {
        return "Teacher{" + super.toString() +
                "isTrained=" + isTrained +
                '}';
    }

    public Teacher(String name, int ID, String pwd, int age, String position) {
        super(name, ID, pwd, age, position);
        this.isTrained = false;
    }

    public boolean isTrained() {
        return isTrained;
    }

    public void setTrained(boolean trained) {
        isTrained = trained;
    }
}
