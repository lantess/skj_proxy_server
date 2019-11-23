package com.s19215;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;

public class IOListener implements Runnable {

    private InputStream in;
    private OutputStream out;

    public IOListener(InputStream in, OutputStream out){
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        try{
            byte[] data = new byte[4096];
            int dataLength;
            do{
                dataLength = in.read(data);
                if(dataLength > 0){
                    out.write(data,0,dataLength);
                    if(in.available()<1)
                        out.flush();
                }
            } while(dataLength>0);
        }catch (SocketTimeoutException e){
            System.out.println("Socket został zamkniety.");
        }
        catch (IOException e){
            System.out.println("Wystąpił błąd podczas przesyłu danych:\n\t\t"+e.getMessage());
        }
    }
}
