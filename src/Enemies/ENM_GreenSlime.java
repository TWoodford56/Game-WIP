package Enemies;

import entity.Entity;
import main.GamePanel;
import Object.OBJ_SlimeBall;
import Object.OBJ_GoldCoin;
import Object.OBJ_Arrow;
import Object.OBJ_Potion_Blue;

import java.util.Random;

public class ENM_GreenSlime extends Entity {

    GamePanel gp;

    public ENM_GreenSlime(GamePanel gp){
        super(gp);

        this.gp = gp;

        name = "GreenSlime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp = 2;
        projectile = new OBJ_SlimeBall(gp);

        bounds.x = 3;
        bounds.y = 18;
        bounds.width = 42;
        bounds.height = 30;
        boundsDefaultX = bounds.x;
        boundsDefaultY = bounds.y;
        type = type_Enemy;

        getImage();
    }

    public void getImage(){

        up1 = setup("/Enemies/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/Enemies/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/Enemies/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/Enemies/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/Enemies/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/Enemies/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/Enemies/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/Enemies/greenslime_down_2", gp.tileSize, gp.tileSize);
        damaged = setup("/Enemies/greenslimeDamaged", gp.tileSize, gp.tileSize);

    }

    public void update(){
        super.update();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if(!onTrack && tileDistance < 5){
            int i = new Random().nextInt(100)+1;
            if(i > 25){
                onTrack = true;
            }
        }
        if(onTrack && tileDistance > 16){
            onTrack = false;
        }

    }

    public void setAction() {
        if(onTrack) {

            int goalCol = (gp.player.worldX + gp.player.bounds.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.bounds.y)/gp.tileSize;

            searchPath(goalCol, goalRow);

            int j = new Random().nextInt(100) + 1;
            if (j > 99 && !projectile.alive && shotAvailableCounter == 30) {
                projectile.set(worldX, worldY, direction, true, this);
                gp.projectiles.add(projectile);
                shotAvailableCounter = 0;
            } else {

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
    }

    public void damageReaction(){
        actionLock = 0;
        onTrack = true;
    }


    public void checkDrop(){

        //throwing the dice
        int i = new Random().nextInt(100)+1;

        //set drops
        if(i < 50){
            dropItem(new OBJ_GoldCoin(gp));
        }
        if(i >= 50 && i < 75){
            dropItem(new OBJ_Arrow(gp));
        }
        if(i >= 75 && i < 100){
            dropItem(new OBJ_Potion_Blue(gp));
        }



    }
}
