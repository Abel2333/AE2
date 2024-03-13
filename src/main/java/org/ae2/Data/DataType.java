package org.ae2.Data;

public enum DataType {
    TEACHER("Teacher.json", "org.ae2.Data.Teacher", true),
    DIRECTOR("Director.json", "org.ae2.Data.Director", true),
    ADMINISTRATOR("Administrator.json", "org.ae2.Data.Administrator", true),
    REQUIREMENT("Requirement.json", "org.ae2.Data.Requirement", false);
    private final String fileName;
    private final String className;
    private final boolean isStuff;

    DataType(String fileName, String className, boolean isStuff) {
        this.fileName = fileName;
        this.className = className;
        this.isStuff = isStuff;
    }

    public String getFileName() {
        return fileName;
    }

    public String getClassName() {
        return className;
    }

    public boolean isStuff() {
        return isStuff;
    }

    private static String removeFirstWordAndSpace(String input) {
        // Find the end position of first word.
        int firstWordEndIndex = input.indexOf(' ');

        // If it finds the space, remove all byte before space (include space).
        if (firstWordEndIndex != -1) {
            return input.substring(firstWordEndIndex + 1).trim();
        }

        // If no space is found, it means that there is
        // only one word or no word in the string
        return "";
    }

    public static String getClassName(Object object) {
        // className here looks like "class org.xx.xx"
        String className = String.valueOf(object.getClass());
        // Get "org.xx.xx"
        return DataType.removeFirstWordAndSpace(className);
    }
}
