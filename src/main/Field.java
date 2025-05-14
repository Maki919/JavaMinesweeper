package main;

public class Field {
    private int type = 0;
    private int status = 0; // 0: unclicked, 1: clicked, 2: flagged
    private int x;
    private int y;

    public Field(int type, int status) {
        this.type = type;
        this.status = status;
    }

    public int getType() {return type;}
    public void setType(int type) {this.type = type;}

    public int getStatus() {return status;}
    public void setStatus(int status) {this.status = status;}
}
