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

    public ProxyConnector(Socket client) throws IOException {
        client.setSoTimeout(socketTimeout);
        this.client=client;
        in_client = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out_client = new PrintWriter(client.getOutputStream());
    }

    @Override
    public void run(){
        HTTPCommandParser command = null;
        try{
            String inputLine = in_client.readLine();
            command = new HTTPCommandParser(inputLine);
            if(!command.isOk())
                throw new IOException("Nieprawidłowa komenda.");
            if(command.getCommand().equals("CONNECT"))
                makeNewConnection(command);
            else
                Log.errorLog("Nierozpoznane połączenie.");

        } catch (IOException e) {
            Log.errorLog("Wystąpił problem podczas komunikacji z <"+command.getUrl()+">:\t"+e.getMessage());
        }
    }
    private void makeNewConnection(HTTPCommandParser command) throws IOException{
        Log.connectionLog("Nawiązano połączenie z "+command.getUrl()+" przez "+client.getInetAddress().getHostAddress());
        server = new Socket(command.getUrl(),
                                    command.getPort());
        server.setSoTimeout(socketTimeout);
        out_client.write(HttpMessages.connectionEstablished);
        out_client.flush();
        Thread clientToServer = new Thread(new IOListener(client.getInputStream(),
                                    server.getOutputStream(),
                                    command.getUrl()));

        clientToServer.start();
        new IOListener(server.getInputStream(),
                client.getOutputStream(),
                command.getUrl())
                .run();
        while(clientToServer.isAlive()){

        }
        closeResources();
        Log.connectionLog("Zakończono połączenie z "+command.getUrl()+" rozpoczęte przez "+client.getInetAddress().getHostAddress());
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
