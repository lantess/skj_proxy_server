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
        Log.initialize();
        try{
            new Main(2137);
        }
        catch(IOException e){
            //System.out.println("Problem z nawiązaniem połączenia");
            //e.printStackTrace();
            Log.errorLog("Nie udało się otworzyć portu. Serwer nie został uruchomiony.");
        }
    }

    public Main(int listeningPort) throws IOException {
        connectionRequestListener = new ServerSocket(listeningPort);
        isAlive = true;
        listenToConnections();
    }

    private void listenToConnections() throws IOException{
        while(isAlive){
            Socket newClient = connectionRequestListener.accept();
            ProxyConnector connection = new ProxyConnector(newClient);
            connections.add(connection);
            connection.start();
        }
    }
}
