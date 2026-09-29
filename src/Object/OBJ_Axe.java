package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {

    GamePanel gp;

    public OBJ_Axe(GamePanel gp) {
        super(gp);

        name = "Basic Axe";
        icon = setup("/Objects/Axe", gp.tileSize, gp.tileSize);
        down1 = icon;
        attackValue = 1;
        description = "Woodsman's Axe";
        attackArea.width = 36;
        attackArea.height = 36;
        type = type_axe;
    }
}
