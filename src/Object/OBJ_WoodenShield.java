package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_WoodenShield extends Entity {


    public OBJ_WoodenShield(GamePanel gp) {
        super(gp);

        name = "Shield";
        icon = setup("/Objects/woodenShield",gp.tileSize,gp.tileSize);
        down1 = icon;
        defenseValue = 1;
        type = type_shield;


    }


}
