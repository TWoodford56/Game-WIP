package Object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Chest extends Entity {

    GamePanel gp;
    Entity loot;
    boolean opened;

    public OBJ_Chest (GamePanel gp, Entity loot){
        super(gp);

        this.gp = gp;
        this.loot = loot;

        opened = false;
        type = type_obstacle;
        name = "Chest";
        icon = setup("/Objects/Chest", gp.tileSize, gp.tileSize);
        image2 = setup("/Objects/Open Chest", gp.tileSize, gp.tileSize);
        collision = true;
        down1 = icon;
    }

    public void interact() {
        if(gp.player.hasKey > 0 && !opened){
            opened = true;
            gp.player.reduceConsumables(gp.player.searchItem("Key"));
            hasKey--;
            down1 = image2;

            StringBuilder sb = new StringBuilder();
            sb.append("You opened the chest and found " + loot.name);
            if(!gp.player.canObtain(loot)){
                sb.append("\nYou could not pick it up, your inventory is full");
            } else {
                sb.append("\n You have added it to your inventory");
            }
            sb.append("\n\n The key was lost in use");
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = sb.toString();
        } else if(!opened){
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You need a key to open this Chest";
        } else if(opened){
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You have already looted this Chest";
        }
    }

}
