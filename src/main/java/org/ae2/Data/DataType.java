package org.ae2.Data;

public enum DataType {
    TEACHER("Teacher.json", "org.ae2.Data.Teacher"),
    DIRECTOR("Director.json", "org.ae2.Data.Director");
    private final String fileName;
    private final String className;


    DataType(String fileName, String className) {
        this.fileName = fileName;
        this.className = className;
    }

    public String getFileName() {
        return fileName;
    }

    public String getClassName() {
        return className;
    }
}
