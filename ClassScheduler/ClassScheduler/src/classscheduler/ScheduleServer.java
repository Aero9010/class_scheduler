/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classscheduler;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author erikm
 */
public class ScheduleServer {
     
    public static void main(String[] args){

    ServerSocket link = null;
        try{
            link = new ServerSocket(5454);
            link.setReuseAddress(true);
            
            while(true){
                Socket client = link.accept();
                
                System.out.println("A client has connected "+client.getInetAddress().getHostAddress());
            
                ClientHandler clientSock = new ClientHandler(client);
                new Thread(clientSock).start();
            }
    
        }catch(IOException e){
            e.printStackTrace();
        }
        finally{
            if(link !=null){
                try{
                    link.close();
                }catch(IOException error){
                    error.printStackTrace();
                }
            }
        }
    }//MAIN
        
    private static void runSock(){
        
    }      

private static class ClientHandler implements Runnable {
    private final Socket clientSock;
    
    public ClientHandler(Socket socket){
        this.clientSock = socket;
    }
    
    public void run(){
        
        PrintWriter output = null;
        BufferedReader input = null;
        String[] classSchedule;
        ArrayList<Schedule> ScheduleList = new ArrayList<>();
        
        try{
            output = new PrintWriter(clientSock.getOutputStream(), true);
            
            input = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
            
            String row;
            while((row = input.readLine()) !=null){
                try{
                    System.out.printf("User: %s\n", row);
                    output.println(row);
                    
                if(row.startsWith("STOP")){
                    System.out.println("Terminating...");
                    output.close();
                    input.close();
                    clientSock.close();
                }    
                    
                if(row.startsWith("ADD")){
                    System.out.println("Adding Class");
                    classSchedule = row.split(";");
                    String date = classSchedule[1];
                    String time = classSchedule[2];
                    String name = classSchedule[3]; 
                    String room = classSchedule[4];
                    Schedule classObj = new Schedule(date, time, name, room);
                    ScheduleList.add(classObj);
                    System.out.println("Name: "+name+". Time: "+time+". Date: "+date+". Room: "+room);
                    
                }else if(row.startsWith("REMOVE")){
                    System.out.println("Removing Class");
                    classSchedule = row.split(";");
                    String name = classSchedule[3];
                    for(Schedule classObj: ScheduleList){
                        if(classObj.getName().equals(name)){
                            ScheduleList.remove(classObj);
                        }
                    }
                    
                }else if(row.startsWith("LIST")){
                    if(ScheduleList.isEmpty()){
                        System.out.println("No schedule to show");
                        
                    }else{
                    
                    System.out.println("Listing Classes");
                    classSchedule = row.split(";");
                    String date = classSchedule[1];
                    for(Schedule classObj: ScheduleList){
                        if(classObj.getDate().equals(date)){
                            System.out.println("Name: "+classObj.getName()+". Time: "+classObj.getTime()+". Date: "+classObj.getDate()+". Room: "+classObj.getRoom());
                        }
                    }
                }
                }else{
                    throw new IncorrectActionException("Incorrect Action: use ADD, REMOVE or LIST");
                }
                }catch(IncorrectActionException e){
                    System.out.println(e.getMessage());
                }
            }
            
        }catch(IOException error){
                    error.printStackTrace();
                    }
        
//        finally{
//            try{
//                String row=;
//                if(row.startsWith("STOP")){
//                    output.close();
//                }
//                if(input !=null){
//                    input.close();
//                    clientSock.close();
//                }
//            }catch(IOException error){
//                error.printStackTrace();
//            }
//        }
    }
    
}


}

