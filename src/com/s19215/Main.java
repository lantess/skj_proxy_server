package com.s19215;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            Log.errorLog("Nie udało się otworzyć portu. Serwer nie został uruchomiony.");
        }
    }

    public Main(int listeningPort) throws IOException {
        connectionRequestListener = new ServerSocket(listeningPort);
        isAlive = true;
        Thread exitEvent = new Thread(
                () -> {
                    System.out.println("Aby zamknąć serwer proszę napisać: EXIT");
                    Scanner in = new Scanner(System.in);
                    String str = "";
                    while (isAlive){
                        str = in.nextLine();
                        if(str.equals("EXIT"))
                            isAlive = false;
                    }
                    System.exit(0);
                }
        );
        exitEvent.start();
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
