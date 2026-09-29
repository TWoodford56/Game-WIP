package Object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends Entity {


    public OBJ_Boots (GamePanel gp){
        super(gp);

        name = "Boots";
        icon = setup("/Objects/boots", gp.tileSize, gp.tileSize);
        down1 = icon;

    }

}
