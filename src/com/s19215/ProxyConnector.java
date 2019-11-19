package com.s19215;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProxyConnector extends Thread{
    private Socket client,
                    server;
    private BufferedReader cl_in;
    private PrintWriter cl_out;
    private BufferedReader sv_in;
    private PrintWriter sv_out;
    private boolean isAlive;
    private int cl_nr;
    public ProxyConnector(Socket client, int cl_nr) throws IOException {
        isAlive = true;
        this.client=client;
        this.cl_nr = cl_nr;
        cl_in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        cl_out = new PrintWriter(client.getOutputStream());
        this.start();
    }

    @Override
    public void run(){
        try{
            String inputLine = cl_in.readLine();
            cl_in.readLine();cl_in.readLine();cl_in.readLine();cl_in.readLine(); //to skip the rest of lines
            server = new Socket(inputLine.split(" ")[1].split(":")[0],
            Integer.parseInt(inputLine.split(" ")[1].split(":")[1]));
            System.out.println(server.getInetAddress());
            cl_out.write("HTTP/1.0 200 Connection established\r\nProxy-Agent: ProxyServer/1.0\r\n\r\n");
            cl_out.flush();
            while(isAlive){
                while((inputLine = cl_in.readLine()) != null){
                    System.out.println(inputLine);
                    sv_out.write(inputLine);
                }
                while((inputLine = sv_in.readLine()) != null){
                    cl_out.write(inputLine);
                }
                sv_out.flush();
                cl_out.flush();
            }
        } catch (IOException e) {
            isAlive = false;
        }
    }
}
