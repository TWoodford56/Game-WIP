package main;

import Object.OBJ_Spikes;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];
    boolean canTouchEvent = true;
    int previousEventX;
    int previousEventY;


    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow && map < gp.maxMap) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height  = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;

                if(row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }

    }

    public void checkEvent() {
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if(distance > gp.tileSize){
            canTouchEvent = true;
        }

        if(gp.gameTimer.timeUp){
            canTouchEvent = true;
            resetAllEvents(gp.currentMap);
            deactivateAllSpikeTraps();
            gp.gameTimer.timeUp = false;
        }

        if(canTouchEvent) {
            if (hit(48, 49, "any", 0)) {
                damagePit(48, 48, gp.dialogueState, gp.currentMap);
                if (!gp.gameTimer.running) {
                    activateSpikeTrapsAt(48, 48);
                    gp.gameTimer.running = true;
                    gp.gameTimer.start();
                }
            } else if (hit(25, 26, "down", 0)) {
                healingPool(gp.dialogueState);
            } else if (hit(75, 72, "up", 0)||hit(76,72,"up",0)) {
                teleport(1, 45, 49);
            } else if (hit(45, 49, "down", 1)) {
                teleport(0, 75, 72);
            }
        }
    }

    public boolean hit(int col, int row, String reqDirection, int map) {
        boolean hit = false;

        if(map == gp.currentMap) {
            gp.player.bounds.x = gp.player.worldX + gp.player.bounds.x;
            gp.player.bounds.y = gp.player.worldY + gp.player.bounds.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.bounds.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                }
            }


            gp.player.bounds.x = gp.player.boundsDefaultX;
            gp.player.bounds.y = gp.player.boundsDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

    public void damagePit(int col, int row, int gameState, int map) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You Walked into a trap";
        gp.player.life -= 1;
        eventRect[map][col][row].eventDone = true;
        canTouchEvent = false;
    }


    public void healingPool(int gameState) {

        if(gp.keyHandler.ePressed) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drank from the pool. \nYour life is returning to you";
            gp.player.life = gp.player.maxLife;
            gp.keyHandler.ePressed = false;
            gp.ASetter.setEnemies();
        }
    }

    private void resetAllEvents(int map) {
        for(int col = 0; col < gp.maxWorldCol; col++) {
            for(int row = 0; row < gp.maxWorldRow; row++) {
                eventRect[map][col][row].eventDone = false;
            }
        }
    }

    // Activate spike traps at specific location
    private void activateSpikeTrapsAt(int tileCol, int tileRow) {
        int targetWorldX = tileCol * gp.tileSize;
        int targetWorldY = tileRow * gp.tileSize;

        for(int i = 0; i < gp.obj[1].length; i++) {
            if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals("Spikes")) {
                if(gp.obj[gp.currentMap][i].worldX == targetWorldX && gp.obj[gp.currentMap][i].worldY == targetWorldY) {
                    OBJ_Spikes trap = (OBJ_Spikes) gp.obj[gp.currentMap][i];
                    trap.activate();
                    canTouchEvent = true;
                }
            }
        }
    }

    // Deactivate all spike traps
    private void deactivateAllSpikeTraps() {
        for(int i = 0; i < gp.obj.length; i++) {
            if(gp.obj[i] != null && gp.obj[gp.currentMap][i].name.equals("Spikes")) {
                OBJ_Spikes trap = (OBJ_Spikes) gp.obj[gp.currentMap][i];
                trap.deactivate();
            }
        }
    }

    private void teleport(int map, int col, int row) {
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize*col;
        gp.player.worldY = gp.tileSize*row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;


    }
}
