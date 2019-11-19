package com.s19215;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private ServerSocket connectionRequestListener;
    private boolean isAlive;
    private List<ProxyConnector> connections = new ArrayList<>();

    public static void main(String[] args) {
        try{
            new Main(2137);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public Main(int listeningPort) throws IOException {
        connectionRequestListener = new ServerSocket(listeningPort);
        isAlive = true;
        int client_nr = 0;
        while(isAlive){
            Socket newClient = connectionRequestListener.accept();
            connections.add(new ProxyConnector(newClient, client_nr++));
        }
    }
}
