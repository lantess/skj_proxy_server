package com.s19215;

public class HTTPCommandParser {
    private String command,
                    url,
                    port;
    private boolean ok;
    public HTTPCommandParser(String s){
        String[] splittedLine = s.split(" ");
        try{
            command = splittedLine[0];
            url = splittedLine[1].substring(0,
                                            splittedLine[1].indexOf(":"));
            port = splittedLine[1].substring(splittedLine[1].indexOf(":")+1);
             ok = true;
        } catch(Exception e){
             ok = false;
        }
    }
    public String getUrl(){
        return this.url;
    }
    public String getCommand(){
        return this.command;
    }
    public Integer getPort(){
        return Integer.parseInt(port);
    }
    public boolean isOk(){
        return ok;
    }
}
