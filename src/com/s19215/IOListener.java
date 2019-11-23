package com.s19215;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;

public class IOListener implements Runnable {

    private InputStream in;
    private OutputStream out;
    private String url;

    public IOListener(InputStream in, OutputStream out, String url){
        this.in = in;
        this.out = out;
        this.url = url;
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
            //nic nie warto robić
        }
        catch (IOException e){
            Log.errorLog("Wystąpił błąd podczas przesyłu danych z "+url+":"+e.getMessage());
        }
    }
}
