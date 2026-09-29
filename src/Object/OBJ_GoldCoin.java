package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_GoldCoin extends Entity {

    GamePanel gp;

    public OBJ_GoldCoin(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_collectables;
        name = "Gold Coin";
        icon = setup("/Objects/GoldCoin", gp.tileSize, gp.tileSize);
        down1 = icon;
        value = 1;
    }

    public void use(Entity e){
        //sound effect
        gp.ui.showMessage("Coins: " + value);
        gp.player.coin += value;
    }


}
