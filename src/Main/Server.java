package networksA1;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


class ConnectionHandler implements Runnable{
    private final Socket clientSocket;
    Board board;
    ConnectionHandler(Socket cs, Board board){
        clientSocket=cs;
        this.board = board;
    }
    public void post(String str){

    }
    public void StringProcessing(String str,PrintWriter out) throws SocketException,IOException{
        String[] processed = str.split(" ");
        switch(processed[0]){
            case "GET":

            break;

            case "POST":

            break;

            case "PIN":

            break;

            case "UNPIN":

            break;

            case "CLEAR":

            break;

            case "DISCONNECT":
            clientSocket.close();
            break;

            default:
            out.println("Invalid Request");


        }
    }

    @Override
    public void run() {
        PrintWriter out = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(board.colors);
            while(true){
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String response = in.readLine();
                this.StringProcessing(response,out);
                out.println(response);
            }
        } catch(SocketException e){
            System.out.println("Clinet Disconnected");
        }catch (IOException e) {
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
class Board {
    int width,height;
    ArrayList<String> colors;
    ArrayList<Note> notes;
    Board(int w,int h){
        notes = new ArrayList<Note>();
        colors = new ArrayList<String>();
        width = w;
        height = h;
    }
    void AddColor(String color){
        colors.add(color);
    }
    void AddNote(Note note){

    }
}



public class Server {
    ArrayList<Note> notes=new ArrayList<Note>();
    public static void main(String[] args) {
        int n = args.length;
        int port =  Integer.parseInt(args[0]);
        int width = Integer.parseInt(args[1]);
        int height =  Integer.parseInt(args[2]);
        Board board = new Board(width, height);
        for(int i=3;i<n;i++){
            board.AddColor(args[i]);
        }
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("The Main.Server is running");
            Socket clientSocket = null;
            while (true) {
                    clientSocket = serverSocket.accept();
                    new Thread(new ConnectionHandler(clientSocket,board)).start();
                    System.err.println("Accepted a client from: "+clientSocket.getInetAddress());
                    ObjectInputStream b = new ObjectInputStream(clientSocket.getInputStream());
                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                  
                 
                  
                    output.println("hola chica");
                    b.close();
                    output.close();
                    clientSocket.close(); }
          
        }catch(IOException e){
	e.printStackTrace();}}
}
