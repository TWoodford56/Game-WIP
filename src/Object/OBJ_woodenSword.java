package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_woodenSword extends Entity {

    public OBJ_woodenSword(GamePanel gp) {
        super(gp);

        name = "Wooden Sword";
        down1 = setup("/Objects/woodenSword",gp.tileSize,gp.tileSize);
        icon = down1;
        description = "Wooden Sword:\nMeant for practicing but can be used to ward off enemies in a pinch.";
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        type = type_sword;

        //Attacking images


    }
}
