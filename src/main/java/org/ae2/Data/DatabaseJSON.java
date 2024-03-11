package org.ae2.Data;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Vector;

public class DatabaseJSON {
    /*
     * Using JSON file as a simple database
     * Load from file once and write to file once.
     * */

    private DataType dataType;
    private final ObjectMapper objectMapper;

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public DatabaseJSON(DataType dataType) {
        this.dataType = dataType;
        this.objectMapper = new ObjectMapper();

        // Format json string.
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    //Overloading of writeData(Vector<Stuff>)
    public void writeData(Stuff[] stuffs) throws JsonProcessingException {
        Vector<Stuff> stuffVector = new Vector<>(java.util.Arrays.asList(stuffs));
        writeData(stuffVector);
    }

    public void writeData(Vector<Stuff> stuffs) throws JsonProcessingException {
        // Write all objects to file at once.
        Path jsonPath = Path.of(this.dataType.getFileName());
        String tempString = objectMapper.writeValueAsString(stuffs);
        try {
            Files.writeString(jsonPath, tempString, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Vector<Stuff> readData() throws ClassNotFoundException {
        Path jsonPath = Path.of(dataType.getFileName());
        if (!Files.exists(jsonPath)) {
            return null;
        }

        try (InputStream inputStream = Files.newInputStream(jsonPath)) {
            // Create JsonParser via JsonFactory()
            JsonParser jsonParser = new JsonFactory().createParser(inputStream);

            // Acquire appropriate class.
            Class<?> clazz = Class.forName(this.dataType.getClassName());
            // ConstructParametricType(Vector.class, clazz) will indicate a composite type.
            // That's equal to Vector<clazz> while clazz is Class<?> and correct class must be Vector<T>.
            return objectMapper.readValue(jsonParser, objectMapper.getTypeFactory().constructParametricType(Vector.class, clazz));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
