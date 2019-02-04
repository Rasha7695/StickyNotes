package networksA1;

public class Note {
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