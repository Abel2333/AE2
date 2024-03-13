package org.ae2.Data;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class GlobalData {
    private static GlobalData globalData;

    private static Map<DataType, Vector<Stuff>> allStuffs;
    private static Vector<Requirement> allRequirements;

    private GlobalData() throws ClassNotFoundException {
        DatabaseJSON databaseJSON = new DatabaseJSON();

        allStuffs = new LinkedHashMap<>();

        for (DataType dataType : DataType.values()) {
            databaseJSON.setDataType(dataType);
            if (dataType.isStuff()) {
                Vector<Stuff> tempStuff = databaseJSON.readData();
                if (tempStuff == null) {
                    tempStuff = new Vector<>();
                }
                allStuffs.put(dataType, tempStuff);
            } else {
                allRequirements = databaseJSON.readData();
                if (allRequirements == null) {
                    allRequirements = new Vector<>();
                }
            }
        }

    }

    public static synchronized void initialize() throws ClassNotFoundException {
        if (globalData == null) {
            globalData = new GlobalData();
        }
    }

    public static Vector<Stuff> getAllTeachers() {
        return allStuffs.get(DataType.TEACHER);
    }

    public static Vector<Requirement> getAllRequirements() {
        return allRequirements;
    }

    public static Stuff getStuff(int ID) {
        for (Map.Entry<DataType, Vector<Stuff>> entry : allStuffs.entrySet()) {
            for (Stuff stuff : entry.getValue()) {
                if (stuff.getID() == ID) {
                    return stuff;
                }
            }
        }
        return null;
    }

    public static Stuff getStuff(String name) {
        for (Map.Entry<DataType, Vector<Stuff>> entry : allStuffs.entrySet()) {
            for (Stuff stuff : entry.getValue()) {
                if (stuff.getName().equals(name)) {
                    return stuff;
                }
            }
        }
        return null;
    }

    public static Requirement getRequirement(String ID) {
        for (Requirement requirement : GlobalData.allRequirements) {
            if (ID.equals(requirement.getID())) {
                return requirement;
            }
        }
        return null;
    }

    public static void addStuff(Stuff stuff) {
        String className = DataType.getClassName(stuff);

        for (DataType dataType : DataType.values()) {
            if (dataType.getClassName().equals(className)) {
                Vector<Stuff> stuffs = allStuffs.get(dataType);
                if (!stuffs.add(stuff)) {
                    throw new UnsupportedOperationException("Could not add stuff " + stuff);
                }
            }
        }
    }

    public static void addRequirement(Requirement requirement) {
        if (!allRequirements.add(requirement)) {
            throw new UnsupportedOperationException("Could not add requirement " + requirement.getID());
        }
    }

    public static void deleteStuff(int ID) {
        Stuff stuff = getStuff(ID);
        if (stuff == null) {
            System.out.println("There are no stuff with ID " + ID);
            return;
        }

        String className = DataType.getClassName(stuff);
        for (DataType dataType : DataType.values()) {
            if (dataType.getClassName().equals(className)) {
                Vector<Stuff> stuffs = allStuffs.get(dataType);
                stuffs.remove(stuff);
            }
        }
    }

    public static void deleteRequirement(String ID) {
        Requirement requirement = getRequirement(ID);
        if (requirement == null) {
            System.out.println("There are no requirement with ID " + ID);
            return;
        }

        GlobalData.allRequirements.remove(requirement);
    }

    public static void changeStuff(int ID, Stuff stuff) {
        GlobalData.deleteStuff(ID);
        GlobalData.addStuff(stuff);
    }

    public static void changeRequirement(String ID, Requirement requirement) {
        GlobalData.deleteRequirement(ID);
        GlobalData.addRequirement(requirement);
    }

    public static synchronized void end() throws JsonProcessingException {
        if (globalData == null) {
            return;
        }

        DatabaseJSON databaseJSON = new DatabaseJSON();
        for (Map.Entry<DataType, Vector<Stuff>> entry : allStuffs.entrySet()) {
            databaseJSON.setDataType(entry.getKey());
            databaseJSON.writeData(entry.getValue());
        }
        databaseJSON.setDataType(DataType.REQUIREMENT);
        databaseJSON.writeData(allRequirements);
    }
}
