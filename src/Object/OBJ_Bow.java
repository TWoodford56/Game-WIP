package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bow extends Entity {

    public OBJ_Bow(GamePanel gp) {
        super(gp);

        name = "bow";
        type = type_Bow;
        icon = setup("/Objects/Bow_Basic",gp.tileSize,gp.tileSize);
        down1 = icon;
        description = "Basic Bow";
    }


}
