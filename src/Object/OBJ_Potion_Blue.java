package Object;
import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Blue extends Entity{

    GamePanel gp;

    public OBJ_Potion_Blue(GamePanel gp){
        super(gp);

        this.gp = gp;

        stackable = true;
        value = 5;
        name = "Blue Potion";
        type = type_consumable;
        icon = setup("/Objects/PotionBlue", gp.tileSize, gp.tileSize);
        down1 = icon;
        description = "[Blue Potion] \nHeals you by " + value + ".";
    }

    public void use(Entity e){

        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n " + "your health is recovering";
        e.life += value;

        if(gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
        //potentially add image and sound
    }


}
