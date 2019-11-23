package com.s19215;

public class HttpMessages {
    public final static String serverName = "s19215server";
    public final static String connectionEstablished = "HTTP/1.0 200 Connection established\r\n" +
                                                        "Proxy-Agent: "+serverName+"/1.0\r\n" +
                                                        "\r\n";
    public final static String timeout = "HTTP/1.0 504 Timeout Occured after 10s\n" +
                                            "User-Agent: "+serverName+"/1.0\n" +
                                            "\r\n";
    public final static String ok = "HTTP/1.0 200 OK\n" +
                                    "Proxy-agent: "+serverName+"/1.0\n" +
                                    "\r\n";
    public final static String notFound = "HTTP/1.0 404 NOT FOUND\n" +
                                            "Proxy-agent: "+serverName+"/1.0\n" +
                                            "\r\n";
}
