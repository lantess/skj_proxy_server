package com.s19215;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProxyConnector extends Thread{
    private static final int socketTimeout = 10000;

    private Socket client,
                    server;
    private BufferedReader in_client;
    private PrintWriter out_client;
    private boolean isAlive;

    public ProxyConnector(Socket client) throws IOException {
        isAlive = true;
        client.setSoTimeout(socketTimeout);
        this.client=client;
        in_client = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out_client = new PrintWriter(client.getOutputStream());
    }

    @Override
    public void run(){
        try{
            String inputLine = in_client.readLine();
            HTTPCommandParser command = new HTTPCommandParser(inputLine);
            if(!command.isOk())
                throw new IOException("Nieprawidłowa komenda");
            if(command.getCommand().equals("CONNECT"))
                makeNewConnection(command);
            else
                System.out.println(command.getCommand());

        } catch (IOException e) {
            System.out.println("Wystąpił problem podczas komunikacji z serwerem:\n\t\t"+e.getMessage());
            isAlive = false;
        }
    }
    private void makeNewConnection(HTTPCommandParser command) throws IOException{
        System.out.println("Nawiązano połączenie z "+command.getUrl());
        server = new Socket(command.getUrl(),
                                    command.getPort());
        server.setSoTimeout(socketTimeout);
        //skipUnimportantLines();
        out_client.write(HttpMessages.connectionEstablished);
        out_client.flush();
        System.out.println("Rozpoczęcie komunikacji z "+command.getUrl());
        new Thread(new IOListener(client.getInputStream(),
                                    server.getOutputStream()))
                .start();
        new IOListener(server.getInputStream(),
                        client.getOutputStream())
                .run();
        System.out.println("Zakończenie komunikacji z "+command.getUrl());
        closeResources();
    }
    private void skipUnimportantLines() throws IOException{
        while(in_client.readLine() != null){}
    }
    private void closeResources() throws IOException{
        if(client != null)
            client.close();
        if(server != null)
            server.close();
        if(in_client != null)
            in_client.close();
        if(out_client != null)
            out_client.close();
    }
}
