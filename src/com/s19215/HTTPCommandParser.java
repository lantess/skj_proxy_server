package com.s19215;

public class HTTPCommandParser {
    private String command,
                    url,
                    port,
                    file;
    private boolean ok;
    public HTTPCommandParser(String s){
        if(s==null){
            ok = false;
            return;
        }
        String[] splittedLine = s.split(" ");
        try{
            command = splittedLine[0];
            if(command.equals("CONNECT")){
                url = splittedLine[1].substring(0,splittedLine[1].indexOf(":"));
                port = splittedLine[1].substring(splittedLine[1].indexOf(":")+1);
                file = "";
            }else{
                url = splittedLine[1].substring(splittedLine[1].indexOf("//")+2);
                port = "80";
                file = url.substring(url.indexOf("/"));
                url = url.substring(0, url.indexOf("/"));
            }
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
    public String getFile(){ return file;}
    public boolean isOk(){
        return ok;
    }
}
