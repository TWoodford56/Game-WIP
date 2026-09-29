package Object;

import entity.Entity;
import main.GamePanel;

public class OBJ_StartNote extends Entity {

    public OBJ_StartNote(GamePanel gp) {
        super(gp);

        name = "Start Note";
        icon = setup("/Objects/Start Note", gp.tileSize, gp.tileSize);
        down1 = icon;
        description = "A handwritten note from x";
        type = type_note;
        note = "Centuries ago, monsters and humans lived in peace. Friends; not foe. Neighbours; not enemies. One day, humans began to go missing. \n" +
                "Villages emptied as more and more people vanished overnight. " +
                "Yet no one knew what led to the monsters turning on humans, nor what drove them to capture human souls. \n" +
                "There is a prophecy that one day, a human will reclaim our homes and save our people. \n" +
                "We have been waiting a long time, yet we still hold hope.\n";
    }

}
