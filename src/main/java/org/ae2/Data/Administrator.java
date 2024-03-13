package org.ae2.Data;

import java.util.Vector;

public class Administrator extends Stuff {

    Administrator() {
        super();
    }

    Administrator(String name, int ID, String password, int age) {
        super(name, ID, password, age);
    }

    public void addStuff(Stuff stuff) {
        if (stuff.getSalt() == null) {
            stuff.encryptPassword();
        }
        GlobalData.addStuff(stuff);
    }

    public void deleteStuff(int stuffID) {
        GlobalData.deleteStuff(stuffID);
    }

    public void changeStuff(int stuffID, Stuff stuff) {
        GlobalData.changeStuff(stuffID, stuff);
    }

    public void sendInvitation(String requirementID, String stuffName) {
        Teacher teacher = (Teacher) GlobalData.getStuff(stuffName);
        assert teacher != null;
        teacher.addInvitation(requirementID);
    }

    public Vector<Teacher> findAppropriateTeacher(String requirementID) {
        Requirement requirement = GlobalData.getRequirement(requirementID);
        Vector<Stuff> allTeacher = GlobalData.getAllTeachers();
        Vector<Teacher> satisfiedTeacher = new Vector<>();
        for (Stuff teacher : allTeacher) {
            assert requirement != null;
            if (requirement.isSatisfy(teacher.getAge(), ((Teacher) teacher).getCourses())) {
                satisfiedTeacher.add((Teacher) teacher);
            }
        }
        return satisfiedTeacher;
    }

    public void trainTeacher(String requirementID){
        Requirement requirement = GlobalData.getRequirement(requirementID);
        assert requirement != null;
        Vector<Integer> teachersID = requirement.getInvolvedTeachers();
        for (Integer teacherID: teachersID){
            Teacher teacher = (Teacher) GlobalData.getStuff(teacherID);
            assert teacher != null;
            teacher.setTrained(true);
            GlobalData.changeStuff(teacher.getID(), teacher);
        }
    }
}
