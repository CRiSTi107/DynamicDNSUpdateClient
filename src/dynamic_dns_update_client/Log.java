/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dynamic_dns_update_client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author CRiSTi
 */
public class Log {
    
    public static final String path = "DDNS_Update.log";
    
    private static final String AppStarted = System.lineSeparator() + "!-- Application Started --!" + System.lineSeparator() + "[" + new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(Calendar.getInstance().getTime()) + "] App started." + System.lineSeparator();
    
    public static void WriteConsole(String content)
    {
        System.out.println("[" + new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(Calendar.getInstance().getTime()) + "] " + content);
    }
    
    public static void WriteAppStarted()
    {
        try{
            // Create new file
            File file = new File(path);

            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            // Write in file
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                // Write in file
                bw.write(AppStarted);
                // Close connection
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    public static void Write(String content)
    {
        try{
            // Create new file
            File file = new File(path);

            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            // Write in file
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                // Write in file
                bw.write("[" + new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(Calendar.getInstance().getTime()) + "] " + content + System.lineSeparator());
                // Close connection
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
}
