/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynamic_dns_update_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author CRiSTi
 */
public class Dynamic_DNS_Update_Client {

    public static final int SLEEP_MSEC = 5000;
    private static final String PASSWORD = "TODO: Here goes your password.";
    
    public static String myCurrentIP = null;
    
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) {
        
        Log.WriteAppStarted();
        
        while(true)
        {
            try
            { updateIP(); }
            catch(Exception e)
            { System.out.println(e.getMessage()); }
            
            try
            { Thread.sleep(SLEEP_MSEC); }
            catch(InterruptedException e)
            { System.out.println(e.getMessage()); }
        }   
    }
    
   
    private static boolean firstUse = true;
    
    public static void updateIP() throws Exception
    {
        String myIP = null;
        
        try
        {
            myIP = getIP();
        }
        catch(Exception e)
        {
            // NO INTERNET CONNECTION
            //           OR
            // THE SERVER IS OFFLINE
            
            Log.WriteConsole("Could not connect to server.");
            
            return;
        }
        
        URLBuilder urlBuilder = new URLBuilder("yourdomain.dynu.net",
                                               myIP,
                                               PASSWORD);
        
        
        if(myCurrentIP == null)
        {   
            myCurrentIP = myIP;
        }
        
        
        
        if(firstUse)
        {
            String response = changeIP(urlBuilder.GetURL());
            
            Log.Write(response);
            Log.WriteConsole(response);
            
            firstUse = false;
        }
        
        
        
        if(!myCurrentIP.equals(myIP)) // Our public IP has been changed.
        {
            String response = changeIP(urlBuilder.GetURL());
            
            Log.Write(response);
            Log.WriteConsole(response);
        }
        else
        {
            System.out.println("[" + new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(Calendar.getInstance().getTime()) + "] " + "No change.");
        }
            
        
        
        
        
    }
   
    /**
     * @param url URL connection
     * @return Response from the DYNU(or whatever server you are using).
     * @throws Exception 
     */
    public static String changeIP(String url) throws Exception {
        
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()))) {
            response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);
        }

        return response.toString();
    }
    
    public static String getIP() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                    throw e;
                }
            }
        }
    }
    
    public static class URLBuilder {

        private String hostname;
        private String myip;
        private String password;
        
        URLBuilder(String hostname, String myip, String password) {
            this.hostname = hostname;
            this.myip = myip;
            this.password = password;
        }
        
        public String GetURL() {
            return "https://api.dynu.com/nic/update?hostname=" + hostname + 
                                                   "&myip=" + myip + 
                                                   "&password=" + password;            
        }
        
        
        public String gethostname() {
            return hostname;
        }
        public void sethostname(String hostname) {
            this.hostname = hostname;
        }
             
        public String getmyip() {
            return myip;
        }
        public void setmyip(String myip) {
            this.myip = myip;
        }
        
        public String getpassword() {
            return password;
        }
        public void setpassword(String password) {
            this.password = password;
        }
        
    }
    
}
