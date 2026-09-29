package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_SleepingBag extends Entity {

    GamePanel gp;

    public OBJ_SleepingBag(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Sleeping Bag";
        down1 = setup("/Objects/SleepingBag",gp.tileSize,gp.tileSize);
        icon = down1;
        type = type_consumable;
        down2 = setup("/Player/boy_in_SB",gp.tileSize,gp.tileSize);
    }

    public void use(Entity entity){
        gp.gameState = gp.sleepState;
        gp.player.life = gp.player.maxLife;
        gp.player.sleepingImage(down2);
    }
}
