package Main;
import java.io.BufferedReader;
import java.io.IOException;
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
        while(true){
        String response = in.readLine();
        System.out.println("Main.Server response: " + response);}
    }
}
