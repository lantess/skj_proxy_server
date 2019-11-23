package com.s19215;

import java.io.File;
import java.io.FileWriter;

public class LogFiles {
    private static File error,
                        connection;

    private static FileWriter errorWriter,
                        connectionWriter;

    public static void initialize(){
        error = new File("error.log");
        connection = new File("connection.log");


    }
}
