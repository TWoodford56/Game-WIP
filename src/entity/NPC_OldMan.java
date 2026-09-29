package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;


public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        bounds = new Rectangle(8, 16, 32, 32);  // Adjust values as needed
        boundsDefaultX = bounds.x;
        boundsDefaultY = bounds.y;

        direction = "down";
        speed = 1;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage(){

        up1 = setup("/NPC/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/NPC/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/NPC/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/NPC/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/NPC/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/NPC/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/NPC/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/NPC/oldman_right_2", gp.tileSize, gp.tileSize);

    }

    public void setAction(){

        if(onTrack){
            //If we want to follow player
//            int goalCol = (gp.player.worldX + gp.player.bounds.x)/gp.tileSize;
//            int goalRow = (gp.player.worldY + gp.player.bounds.y)/gp.tileSize;

            int goalCol = 68;
            int goalRow = 72;

            searchPath(goalCol,goalRow);

        } else if (stop) {
            direction = "down";
            speed = 0;
        }
        else{

            actionLock++;

            if (actionLock >= 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; //Picks a number between 1-100
                //Dictates movement worth changing later
                if (i <= 25) {
                    direction = "up";
                } else if (i >= 25 && i < 50) {
                    direction = "down";
                } else if (i >= 50 && i < 75) {
                    direction = "left";
                } else if (i >= 75 && i < 100) {
                    direction = "right";
                }
                actionLock = 0;
            }
        }

    }

    public void setDialogue(){
        dialogues[0] = "1";
        dialogues[1] = "2";
        dialogues[2] = "3";
        dialogues[3] = "4";
    }

    public void speak(){

        super.speak();
        onTrack = true;
    }

}
