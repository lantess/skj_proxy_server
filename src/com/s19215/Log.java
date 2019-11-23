package com.s19215;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Log {
    private static File error,
                        connection;
    public static void initialize(){
        error = new File("error.log");
        connection = new File("connection.log");
        checkIfExists(error);
        checkIfExists(connection);
    }
    private static void checkIfExists(File f){
        try{
            if(!f.exists())
                f.createNewFile();
        }catch(IOException e){

        }
    }
    public synchronized static void errorLog(String s){
        log(s,error);
    }
    public synchronized static void connectionLog(String s){
        log(s,connection);
    }
    private synchronized static void log(String s, File f){
        try{
            OutputStream out = new FileOutputStream(f,true);
            out.write(time().getBytes());
            out.write(s.getBytes());
            out.write('\n');
            out.flush();
            out.close();
        }
        catch(IOException e){
            System.out.println("[LOG]"+time()+s);
        }
    }
    private static String time(){
        return "["+ LocalDate.now()+" "+ LocalTime.now()+"]";
    }

}
