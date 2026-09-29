package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {

    GamePanel gp;

    public OBJ_Key (GamePanel gp) {

        super(gp);

        this.gp = gp;

        stackable = true;
        type = type_key;
        name = "Key";
        icon = setup("/Objects/Key", gp.tileSize, gp.tileSize);
        down1 = icon;
    }

}
