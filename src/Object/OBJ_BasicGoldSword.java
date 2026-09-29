package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BasicGoldSword extends Entity {

    public OBJ_BasicGoldSword(GamePanel gp) {
        super(gp);

        name = "Basic Gold Sword";
        icon = setup("/Objects/sword_basicGold", gp.tileSize, gp.tileSize);
        down1 = icon;
        attackValue = 3;
        description = "Golden Sword";
        attackArea.width = 36;
        attackArea.height = 36;
        type = type_sword;
    }
}
