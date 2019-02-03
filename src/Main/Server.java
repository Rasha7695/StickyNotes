package Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class ConnectionHandler implements Runnable{
    private final Socket clientSocket;
    ConnectionHandler(Socket cs){
        clientSocket=cs;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Welcome Rasha!");
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String response = in.readLine();
            System.out.println("Main.Server response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Note {
    int x,y,w,h;
    Boolean pinned=false;
    Note(int x, int y, int w, int h){
        this.x=x;
        this.y=y;
        this.h=h;
        this.w=w;
    }

    Note(int x, int y, int w, int h, Boolean pinned){
       this(x,y,w,h);
       this.pinned=pinned;
    }

    void SetPinned(Boolean p){
        pinned=p;
    }

    Boolean GetPinned(){
        return pinned;
    }
}

public class Server {
    ArrayList<Note> notes=new ArrayList<Note>();
    static int port=54001;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("The Main.Server is running");
            Socket clientSocket = null;
            while (true) {
                try{
                    clientSocket = serverSocket.accept();
                    new Thread(new ConnectionHandler(clientSocket)).start();
                    System.err.println("Accepted a client from: "+clientSocket.getInetAddress());
                } catch (IOException e) {
                    System.err.println("Failed to accept a connection");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Cannot open socket on port"+port);
            e.printStackTrace();
        }
    }
}
