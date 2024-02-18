/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classscheduler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
/**
 *
 * @author erikm
 */
    
    public class ScheduleClient {
        
        public static void main(String[]args){
            try(Socket socket = new Socket("localhost", 5454)){
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
                String row;
                String serverRow;
                
                while( ( row = console.readLine() ) !=null) {
                   if(row.equals("STOP")){
                       output.println("TERMINATE");
                       socket.close();
                       
                   }else{
                       output.println(row);
                   }
                }
//                while( (serverRow = input.readLine()) !=null ){
//                    System.out.printf("Server: %s\n", serverRow);
//                    output.println(serverRow);
//                    
//                }
            }catch(IOException error){
                error.printStackTrace();
                
            }
            
            
        }

    }
    
