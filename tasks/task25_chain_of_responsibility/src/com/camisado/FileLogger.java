package com.camisado;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class FileLogger extends Logger {

    String filename;

    public FileLogger(int level, String filename){
        this.level = level;
        this.filename = filename;
    }

    @Override
    protected void write(String message) throws IOException {
        List<String> messages = Arrays.asList(message);
        Files.write(Paths.get(filename), messages, StandardOpenOption.CREATE);
        System.out.println("File Logger: " + message);
    }
}