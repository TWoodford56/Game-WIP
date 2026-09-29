package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pause extends Entity {

    public OBJ_Pause(GamePanel gp) {
        super(gp);

        name = "Pause";
        image = setup("/UI/Pause Button", gp.tileSize, gp.tileSize);
        image2 = setup("/UI/Play Button", gp.tileSize, gp.tileSize);

    }
}
