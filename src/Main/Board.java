package networksA1;

import java.util.ArrayList;

public class Board {
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