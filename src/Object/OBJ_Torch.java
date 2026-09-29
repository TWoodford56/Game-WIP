package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Torch extends Entity {

    public OBJ_Torch(GamePanel gp){
        super(gp);

        type = type_light;
        name = "torch";
        down1 = setup("/Objects/Torch5",gp.tileSize,gp.tileSize);
        icon = down1;
        lightRadius = 250;
    }
}
