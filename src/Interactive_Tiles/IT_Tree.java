package Interactive_Tiles;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_Tree extends InteractiveTile{

    GamePanel gp;

    public IT_Tree(GamePanel gp, int col, int row){
        super(gp, col, row);
        this.gp = gp;
        this.worldX = gp.tileSize*col;
        this.worldY = gp.tileSize*row;
        life = 3;

        down1 = setup("/Interactive Tiles/Tree", gp.tileSize, gp.tileSize);
        destructible = true;
    }

    public boolean correctWeapon(Entity e) {
        boolean isCorrect = false;
        if (e.currentWeapon.type == type_axe) {
            isCorrect = true;
        }
        return isCorrect;
    }

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        tile.destructible = true;
        return tile;
    }

    public Color getParticleColor(){
        Color color = new Color(60,40,30);
        return color;
    }

    public int getParticleSize(){
        int size = 8;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 2;
        return speed;
    }

    public int getParticelMaxLife(){
        int maxLife = 8;
        return maxLife;
    }
}
