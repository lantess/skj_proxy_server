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
    public ProxyConnector(Socket client) throws IOException {
        isAlive = true;
        this.client=client;
        cl_in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        cl_out = new PrintWriter(client.getOutputStream());
        this.start();
    }

    @Override
    public void run(){
        try{
            String inputLine = cl_in.readLine();
            cl_in.readLine();cl_in.readLine();cl_in.readLine();
            server = new Socket(inputLine.split(" ")[1].split(":")[0],
                    Integer.parseInt(inputLine.split(" ")[1].split(":")[1]));
            System.out.println(server.getInetAddress());
        } catch (IOException e) {
            isAlive = false;
        }
    }
}
