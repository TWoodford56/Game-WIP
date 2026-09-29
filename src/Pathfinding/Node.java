package Pathfinding;

public class Node {

    Node node;
    public int col;
    public int row;
    int g;
    int h;
    int f;
    boolean solid;
    boolean open;
    boolean checked;
    Node parent;

    public Node(int col, int row){
        this.col = col;
        this.row = row;
    }


}
