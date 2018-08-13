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

    public static final int SLEEP_TIME = 5000;
    
    private static final String USER = "usr";
    private static final String PASSWORD = "pswd";
    private static final String DOMAIN = "domain";
    
    public static String myCurrentIP = null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        WriteConsole("App Started");
        
        while(true)
        {
            try
            { updateIP(); }
            catch(Exception e)
            { System.out.println(e.getMessage()); }
            
            try
            { Thread.sleep(SLEEP_TIME); }
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
            
            WriteConsole("Could not connect to server: " + e.getMessage());
            
            return;
        }
        if(myCurrentIP == null)
        {   
            myCurrentIP = myIP;
        }
        
        
        
        if(firstUse)
        {
            String response = changeIP("https://dynamicdns.gear.host/api/set.php?domain=" + DOMAIN + 
                                                                           "&ip=" + myIP + 
                                                                           "&user=" + USER + 
                                                                           "&password=" + PASSWORD);
            
            WriteConsole(response);
            
            firstUse = false;
        }
        
        
        
        if(!myCurrentIP.equals(myIP)) // Our public IP has been changed.
        {
            String response = changeIP("https://dynamicdns.gear.host/api/set.php?domain=" + DOMAIN + 
                                                                            "&ip=" + myIP + 
                                                                            "&user=" + USER + 
                                                                            "&password=" + PASSWORD);
            
            WriteConsole(response);
        }
        else
        {
            WriteConsole("No change");
        }
    }
   
    /**
     * Writes log to console
     * @param content
     */
    public static void WriteConsole(String content) {
        System.out.println("[" + new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(Calendar.getInstance().getTime()) + "] " + content);
    }
    
    /**
     * Send a Request to server to change IP.
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
    
    /**
     * Send a Request to get current IP.
     * @return
     * @throws Exception
     */
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
    
}
