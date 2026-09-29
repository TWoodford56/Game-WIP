package main;

import Enemies.ENM_GreenSlime;
import Interactive_Tiles.IT_Tree;
import Object.OBJ_Key;
import Object.OBJ_Spikes;
import Object.OBJ_Arrow;
import Object.OBJ_Potion_Blue;
import Object.OBJ_Bow;
import Object.OBJ_Chest;
import Object.OBJ_GoldCoin;
import Object.OBJ_Axe;
import Object.OBJ_StartNote;
import entity.NPC_OldMan;
import Object.OBJ_Torch;
import Object.OBJ_SleepingBag;


public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject(){
        int mapNum = 0;
        int i=0;

        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Arrow(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*78;
        gp.obj[mapNum][i].worldY = gp.tileSize*78;
        i++;

        gp.obj[mapNum][i] = new OBJ_StartNote(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*75;
        gp.obj[mapNum][i].worldY = gp.tileSize*93;
        i++;

        gp.obj[mapNum][i] = new OBJ_SleepingBag(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*75;
        gp.obj[mapNum][i].worldY = gp.tileSize*94;
        i++;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*75;
        gp.obj[mapNum][i].worldY = gp.tileSize*84;
        i++;
        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*75;
        gp.obj[mapNum][i].worldY = gp.tileSize*82;
        i++;
//
//        gp.obj[mapNum][i] = new OBJ_Spikes(gp);
//        gp.obj[mapNum][i].worldX = gp.tileSize*48;
//        gp.obj[mapNum][i].worldY = gp.tileSize*83;
//        i++;
//
//        gp.obj[mapNum][i] = new OBJ_BasicIronSword(gp);
//        gp.obj[mapNum][i].worldX = gp.tileSize*75;
//        gp.obj[mapNum][i].worldY = gp.tileSize*72;
        i++;
//
        gp.obj[mapNum][i] = new OBJ_Arrow(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*73;
        gp.obj[mapNum][i].worldY = gp.tileSize*85;
        i++;

        gp.obj[mapNum][i] = new OBJ_Arrow(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*72;
        gp.obj[mapNum][i].worldY = gp.tileSize*86;
        i++;

        gp.obj[mapNum][i] = new OBJ_Arrow(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*71;
        gp.obj[mapNum][i].worldY = gp.tileSize*85;
        i++;

        gp.obj[mapNum][i] = new OBJ_Bow(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*80;
        gp.obj[mapNum][i].worldY = gp.tileSize*86;

    }

    public void setNPC(){
        int mapNum = 0;
        gp.NPC[mapNum][0] = new NPC_OldMan(gp);
        gp.NPC[mapNum][0].worldX = gp.tileSize * 72;
        gp.NPC[mapNum][0].worldY = gp.tileSize * 75;

    }

    public void setEnemies(){
        int i = 0;
        int mapNum = 0;
        gp.enemies[mapNum][i] = new ENM_GreenSlime(gp);
        gp.enemies[mapNum][i].worldX = gp.tileSize * 78;
        gp.enemies[mapNum][i].worldY = gp.tileSize * 72;
        i++;
        gp.enemies[mapNum][i] = new ENM_GreenSlime(gp);
        gp.enemies[mapNum][i].worldX = gp.tileSize * 95;
        gp.enemies[mapNum][i].worldY = gp.tileSize * 94;
        i++;
    }


    public void setInteractiveTiles(){
        int i = 0;
        int mapNum = 0;
        //Set temp for wedge 1
        int adj = 76; //adjusts for where we want the trees on the map
        int width = 10; //width of trees

        //Intro Forest wedge 1
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < width; col++) {
                gp.iTile[mapNum][i] = new IT_Tree(gp, col+adj, row+89);
                i++;
            }
            adj++;
            width--;
        }

        //Set temp for wedge 2
        width = 10;
        adj = 65;

        //intro forest wedge 2
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < width; col++) {
                gp.iTile[mapNum][i] = new IT_Tree(gp, col+adj, row+89);
                i++;
            }
            width--;
        }




    }
}
