package com.s19215;

public class HttpMessages {
    public final static String serverName = "s19215server";
    public final static String connectionEstablished = "HTTP/1.0 200 Connection established\r\n" +
                                                        "Proxy-Agent: "+serverName+"/1.0\r\n" +
                                                        "\r\n";
    public final static String ok = "HTTP/1.0 200 OK\n" +
                                    "Proxy-agent: "+serverName+"/1.0\n" +
                                    "\r\n";
    public final static String httpConnectionRequestData = "User-Agent:"+serverName+"\r\n" +
                                                            "Accept: text/html\r\n" +
                                                            "Accept-Language: en-US\r\n" +
                                                            "Connection: keep-alive\r\n" +
                                                            "\r\n";
    public static String getHTTPConnection(String command, String path, String hostname){
        return ""+command+" "+path+" HTTP/1.1\r\n" +
                "Host: "+hostname+"\r\n"+
                httpConnectionRequestData;
    }
}
