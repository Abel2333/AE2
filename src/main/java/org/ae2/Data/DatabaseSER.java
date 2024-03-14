package org.ae2.Data;

import java.io.*;
import java.util.Vector;

public class DatabaseSER {
    /*
     * Using SER file as a simple database
     * Load from file once and write to file once.
     * */

    private DataType dataType;

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public DatabaseSER(){
        this.dataType = null;
    }
    public DatabaseSER(DataType dataType) {
        this.dataType = dataType;
    }

    public void writeData(Vector<?> dataVector) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.dataType.getFileName()))) {
            out.writeObject(dataVector);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Vector<T> readData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.dataType.getFileName()))) {
            return (Vector<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
