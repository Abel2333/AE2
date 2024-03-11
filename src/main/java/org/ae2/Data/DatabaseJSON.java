package org.ae2.Data;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DatabaseJSON {

    private Path jsonPath;
    private final ObjectMapper objectMapper;

    // TODO: use enum rather than string
    public void setJsonPath(String jsonPath) {
        this.jsonPath = Path.of(jsonPath);
    }

    public DatabaseJSON(String jsonPath) throws FileNotFoundException {
        this.jsonPath = Path.of(jsonPath);

        /*if (!jsonPath.exists()) {
            throw new FileNotFoundException(jsonPath + " is not a file or directory.");
        }*/

        this.objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    // Use stuff vector first.
    // TODO: replace vector with GlobalData
    public void writeData(Stuff[] stuffs) throws JsonProcessingException {
        String tempString = objectMapper.writeValueAsString(stuffs);
        try {
            Files.writeString(this.jsonPath, tempString, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Stuff[] readData() {
        Stuff
    }


    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TeacherFactory teacherFactory = new TeacherFactory();
        Teacher[] teachers = new Teacher[2];
        teachers[0] = (Teacher) teacherFactory.createStuff("testStuff", 123, "112233",21, "testPosition");
        teachers[1] = (Teacher) teacherFactory.createStuff("testStuff1", 1234, "1122334",22, "testPosition1");
        String str = objectMapper.writeValueAsString(teachers);
        System.out.println(str);
    }
}
