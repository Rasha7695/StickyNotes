package Main;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;



class ConnectionHandler implements Runnable{
    private final Socket clientSocket;
    Board board;
    ConnectionHandler(Socket cs, Board board){
        clientSocket=cs;
        this.board = board;
    }

    public String pin(String str){
        String response = "invalid request";
        String[] request = str.split(" ");
        if(request.length!=2){
            return response;
        }
        int x = Integer.parseInt(request[0]);
        int y = Integer.parseInt(request[1]);
        for(int i=0;i<board.notes.size();i++){
            Note note = board.notes.get(i);
            if(note.containsX(x) && note.containsY(y)){
                note.addPin();
            }
        }
        return response;
    }

    public String unpin(String str){
        String response = "invalid request";
        String[] request = str.split(" ");
        if(request.length!=2){
            return response;
        }
        int x = Integer.parseInt(request[0]);
        int y = Integer.parseInt(request[1]);
        Pin p = new Pin(x,y);
        if(!board.pins.contains(p)){
            return "This pin doesn't exist.";
        }
        for(int i=0;i<board.notes.size();i++){
            Note note = board.notes.get(i);
            if(note.containsX(x) && note.containsY(y)){
                note.removePin();
            }
        }
        return response;
    }

    public String clear(String str){
        if(!str.equals("CLEAR")){
            return "invalid request";
        }
        board.pins.clear();
        Iterator itr = board.notes.iterator(); 
        while (itr.hasNext()) 
        { 
            Note note = (Note)itr.next(); 
            if (!note.isPinned()) 
                itr.remove(); 
        } 
        return "Unpinned notes removed.";
    }

    public String getPinned(){
        String response = "";
        for(int i=0;i<board.notes.size();i++){
            if(board.notes.get(i).isPinned()){
                response+="*"+board.notes.get(i).getMessage()+"\n";
            }
        }
        if(response.length()==0){
            response = "No pinned messages.";
        }
        return response;
    }

    public String get(String str){
        String[] request = str.split(" ");
        String response = "invalid request";
        if(request.length==2){
            if(request[1].equals("PINS")){
                response = getPinned();
            }
        } else{
        request = str.split(" ",5);
        ArrayList<Note> requestedNotes = new ArrayList<Note>();
        for(int i=0;i<board.notes.size();i++){
            Note note = board.notes.get(i);
            if(!request[1].equals("ALL")){
                String color = request[1];
                if(!board.colors.contains(color)){
                    response = "No such color.";
                    break;
                }
                if(!note.getColor().equals(color)){
                    continue;
                }
            }
            if(!request[2].equals("ALL")){
                int x = Integer.parseInt(request[2]);
                if(x<0 || x>board.width){
                    response = "Out of bounds error. Check your coordinates.";
                    break;
                }
                if(!note.containsX(x)){
                    continue;
                }
               
            }
            if(!request[3].equals("ALL")){
                int y = Integer.parseInt(request[3]);
                if(y<0 || y>board.height){
                    response = "Out of bounds error. Check your coordinates.";
                    break;
                }
                if(!note.containsY(y)){
                    continue;
                }
            }
            if(!request[4].equals("ALL")){
                String sub = request[4];
                if(!note.getMessage().contains(sub)){
                    continue;
                }
            }
            requestedNotes.add(note);
        }
        if(requestedNotes.size()==0){
            response = "No notes match your request.";
        }
    }

        return response;
    }
    public void StringProcessing(String str,PrintWriter out) throws SocketException,IOException{
        String[] processed = str.split(" ");
        String response = "invalid request";
        switch(processed[0]){
            case "GET":
            response = get(str);
            out.println(response);
            break;

            case "POST":

            break;

            case "PIN":
            response = pin(str);
            out.println(response);
            break;

            case "UNPIN":
            response = unpin(str);
            out.println(response);
            break;

            case "CLEAR":
            response = clear(str);
            out.println(response);
            break;

            case "DISCONNECT":
            response = "client disconnected";
            out.println(response);
            clientSocket.close();
            break;

            default:
            out.println(response);


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
            System.out.println("Client Disconnected");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Note {
    int x,y,w,h;
    String message,color;
    int pinsNum;
    Note(int x, int y, int w, int h){
        this.x=x;
        this.y=y;
        this.h=h;
        this.w=w;
        this.color = "red";
        this.pinsNum = 0;
    }

    Note(int x, int y, int w, int h,String color,String message){
       this(x,y,w,h);
       this.color = color;
       this.message = message;
    }

    boolean containsX(int x){
        if(x>this.x+w || x<this.x){
            return false;
        }
        return true;
    }
    boolean containsY(int y){
        if(y>this.y+h || y<this.y){
            return false;
        }
        return true;
    }

    String getColor(){
        return this.color;
    }
    void addPin(){
        pinsNum++;
    }
    void removePin(){
        pinsNum--;
    }
    String getMessage(){
        return message;
    }
    Boolean isPinned(){
        if(pinsNum>0) return true;
        return false;
    }
}
class Pin{
    int x,y;
    Pin(int x,int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object o) { 
  
        if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof Pin)) { 
            return false; 
        } 
          
        Pin c = (Pin) o; 
        if(this.x!=c.x || this.y!=c.y) return false; 
        return true;
    } 
}
class Board {
    int width,height;
    ArrayList<String> colors;
    ArrayList<Note> notes;
    ArrayList<Pin> pins;
    Board(int w,int h){
        notes = new ArrayList<Note>();
        colors = new ArrayList<String>();
        pins = new ArrayList<Pin>();
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
                try{
                    clientSocket = serverSocket.accept();
                    new Thread(new ConnectionHandler(clientSocket,board)).start();
                    System.err.println("Accepted a client from: "+clientSocket.getInetAddress());
                } catch (IOException e) {
                    System.err.println("Failed to accept a connection");
                }
            }
     } catch(IOException e){

     }
    }
}

