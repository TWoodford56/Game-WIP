package main;

import entity.Entity;

public class CollisionCheck {

    GamePanel gp;

    public CollisionCheck(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity){

        int entityLeftWorldX = entity.worldX + entity.bounds.x;
        int entityRightWorldX = entity.worldX + entity.bounds.x + entity.bounds.width;
        int entityTopWorldY = entity.worldY + entity.bounds.y;
        int entityBottomWorldY = entity.worldY + entity.bounds.y + entity.bounds.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tm.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if(gp.tm.tile[tileNum1].collision == true || gp.tm.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tm.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tm.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if(gp.tm.tile[tileNum1].collision == true || gp.tm.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tm.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tm.tile[tileNum1].collision == true || gp.tm.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, Boolean player){

        int index = 999;

        for(int i = 0; i < gp.obj[1].length; i++){

            if(gp.obj[gp.currentMap][i] != null){
                //get Entity's bounds
                entity.bounds.x = entity.worldX + entity.bounds.x;
                entity.bounds.y = entity.worldY + entity.bounds.y;

                //get the objects bounds
                gp.obj[gp.currentMap][i].bounds.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].bounds.x;
                gp.obj[gp.currentMap][i].bounds.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].bounds.y;

                switch(entity.direction){
                    case "up":
                        entity.bounds.y -= entity.speed;
                        break;
                    case "down":
                        entity.bounds.y += entity.speed;
                        break;
                    case "left":
                        entity.bounds.x -= entity.speed;
                        break;
                    case "right":
                        entity.bounds.x += entity.speed;
                        break;
                }
                if(entity.bounds.intersects(gp.obj[gp.currentMap][i].bounds)){
                    if(gp.obj[gp.currentMap][i].collision == true){
                        entity.collisionOn = true;
                    }
                    if(player == true){
                        index = i;
                    }
                }

                entity.bounds.x = entity.boundsDefaultX;
                entity.bounds.y = entity.boundsDefaultY;
                gp.obj[gp.currentMap][i].bounds.x = gp.obj[gp.currentMap][i].boundsDefaultX;
                gp.obj[gp.currentMap][i].bounds.y = gp.obj[gp.currentMap][i].boundsDefaultY;
            }

        }

        return index;
    }

    //NPC Player Collision
    public int checkEntity(Entity entity, Entity[][] target){
        int index = 999;

        for(int i = 0; i < target[1].length; i++){

            if(target[gp.currentMap][i] != null){
                //get Entity's bounds
                entity.bounds.x = entity.worldX + entity.bounds.x;
                entity.bounds.y = entity.worldY + entity.bounds.y;

                //get the objects bounds
                target[gp.currentMap][i].bounds.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].bounds.x;
                target[gp.currentMap][i].bounds.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].bounds.y;

                switch(entity.direction){
                    case "up":
                        entity.bounds.y -= entity.speed;
                        break;
                    case "down":
                        entity.bounds.y += entity.speed;
                        break;
                    case "left":
                        entity.bounds.x -= entity.speed;
                        break;
                    case "right":
                        entity.bounds.x += entity.speed;
                        break;
                }
                if(entity.bounds.intersects(target[gp.currentMap][i].bounds)){
                    if(target[gp.currentMap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.bounds.x = entity.boundsDefaultX;
                entity.bounds.y = entity.boundsDefaultY;
                target[gp.currentMap][i].bounds.x = target[gp.currentMap][i].boundsDefaultX;
                target[gp.currentMap][i].bounds.y = target[gp.currentMap][i].boundsDefaultY;
            }

        }

        return index;
    }

    public boolean checkPlayer(Entity entity){

        boolean contactPlayer = false;

        //get Entity's bounds
        entity.bounds.x = entity.worldX + entity.bounds.x;
        entity.bounds.y = entity.worldY + entity.bounds.y;

        //get the objects bounds
        gp.player.bounds.x = gp.player.worldX + gp.player.bounds.x;
        gp.player.bounds.y = gp.player.worldY + gp.player.bounds.y;

        switch(entity.direction){
            case "up":
                entity.bounds.y -= entity.speed;
                break;
            case "down":
                entity.bounds.y += entity.speed;
                break;
            case "left":
                entity.bounds.x -= entity.speed;
                break;
            case "right":
                entity.bounds.x += entity.speed;
                break;
        }
        if(entity.bounds.intersects(gp.player.bounds)){
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.bounds.x = entity.boundsDefaultX;
        entity.bounds.y = entity.boundsDefaultY;
        gp.player.bounds.x = gp.player.boundsDefaultX;
        gp.player.bounds.y = gp.player.boundsDefaultY;

        return contactPlayer;
    }
}
