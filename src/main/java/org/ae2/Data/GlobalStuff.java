package org.ae2.Data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class GlobalStuff {
    private static GlobalStuff globalStuff;

    private static Map<DataType, Vector<Stuff>> allStuffs;

    private GlobalStuff() throws ClassNotFoundException {
        DatabaseJSON databaseJSON = new DatabaseJSON();

        allStuffs = new LinkedHashMap<>();

        for (DataType dataType : DataType.values()) {
            databaseJSON.setDataType(dataType);
            allStuffs.put(dataType, databaseJSON.readData());
        }
    }

    public static synchronized void initialize() throws ClassNotFoundException {
        if (globalStuff == null) {
            globalStuff = new GlobalStuff();
        }
    }

    public static Stuff getStuff(int ID) {
        for (Map.Entry<DataType, Vector<Stuff>> entry : allStuffs.entrySet()) {
            for (Stuff stuff : entry.getValue()) {
                if (stuff.getID() == ID) {
                    return stuff;
                }
            }
        }
        throw (new NullPointerException("There are no stuff with ID " + ID));
    }

    public static boolean addStuff(Stuff stuff) {
        String className = DataType.getClassName(stuff);

        for (DataType dataType : DataType.values()) {
            if (dataType.getClassName().equals(className)) {
                Vector<Stuff> stuffs = allStuffs.get(dataType);
                return stuffs.add(stuff);
            }
        }

        return false;
    }

    public static boolean deleteStuff(int ID) {
        Stuff stuff = getStuff(ID);

        String className = DataType.getClassName(stuff);
        for (DataType dataType : DataType.values()) {
            if (dataType.getClassName().equals(className)) {
                Vector<Stuff> stuffs = allStuffs.get(dataType);
                return stuffs.remove(stuff);
            }
        }
        return false;
    }


}
