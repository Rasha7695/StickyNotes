package Main;
import java.io.*;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("Enter the IP address of a machine running the date server:");
        String serverAddress = new Scanner(System.in).nextLine();
        int port = new Scanner(System.in).nextInt();
        Socket socket = new Socket(serverAddress, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message = "";
        try{
            while(true){
                String response=in.readLine();
                System.out.println("Server response: " + response);
                message = new Scanner(System.in).nextLine();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
            if(message.equals("DISCONNECT")){
                socket.close();
                
            }
            }
        } catch(IOException e){
            if(message.equals("DISCONNECT")){
                System.out.println("Disconnected");
            }
            else{
                System.out.println("Server shutdown.");
                socket.close();
            }  
        }
        
    }
}
