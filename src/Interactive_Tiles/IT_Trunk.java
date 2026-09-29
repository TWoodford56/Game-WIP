package Interactive_Tiles;

import entity.Entity;
import main.GamePanel;

public class IT_Trunk extends InteractiveTile {

    GamePanel gp;

    public IT_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        life = 2;

        down1 = setup("/Interactive Tiles/TreeStump", gp.tileSize, gp.tileSize);
        destructible = true;
        invincible = true;
    }

    public boolean correctWeapon(Entity e) {
        boolean isCorrect = false;
        if (e.currentWeapon.type == type_axe) {
            isCorrect = true;
        }
        return isCorrect;
    }
    public InteractiveTile getDestroyedForm() {
        return null;
    }
}

