package main;

public class Field {
    public static final int TYPE_BOMB = -1;
    public static final int TYPE_FLAG = -3;
    public static final int STATUS_HIDDEN = 0;
    public static final int STATUS_REVEALED = 1;
    public static final int STATUS_FLAGGED = 2;

    private int type;
    private int status; // 0: unclicked, 1: clicked, 2: flagged

    public Field(int type, int status) {
        this.type = type;
        this.status = status;
    }

    public int getType() {return type;}
    public void setType(int type) {this.type = type;}

    public int getStatus() {return status;}
    public void setStatus(int status) {this.status = status;}
}
