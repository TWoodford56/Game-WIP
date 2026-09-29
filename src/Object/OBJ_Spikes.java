package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Spikes extends Entity {

    public OBJ_Spikes (GamePanel gp){

        super(gp);

        name = "Spikes";
        image = setup("/Objects/SpikeTrapOff", gp.tileSize, gp.tileSize);
        image2 = setup("/Objects/SpikeTrapOn", gp.tileSize, gp.tileSize);


    }

    public void activate(){
        activated = true;
    }

    public void deactivate(){
        activated = false;
    }

}
