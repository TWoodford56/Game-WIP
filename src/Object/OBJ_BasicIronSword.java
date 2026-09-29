package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BasicIronSword extends Entity {

    public OBJ_BasicIronSword(GamePanel gp) {
        super(gp);

        name = "Basic Iron Sword";
        icon = setup("/Objects/sword_basicIron",gp.tileSize,gp.tileSize);
        down1 = icon;
        attackValue = 2;
        description = "Iron Sword";
        attackArea.width = 36;
        attackArea.height = 36;
        type = type_sword;

        //attacking images
        attackUp1 = setup("/Player/boy_attackIronSword_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/Player/boy_attackIronSword_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/Player/boy_attackIronSword_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/Player/boy_attackIronSword_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/Player/boy_attackIronSword_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/Player/boy_attackIronSword_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/Player/boy_attackIronSword_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/Player/boy_attackIronSword_right_2", gp.tileSize*2, gp.tileSize);
    }
}
