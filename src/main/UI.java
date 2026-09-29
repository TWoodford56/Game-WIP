package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Object.OBJ_Pause;
import Object.OBJ_Heart;
import entity.Entity;


public class UI {


    //For pause and play
    BufferedImage pauseImage, playImage;

    //For HUD
    BufferedImage heartFull, heartHalf, heartBlank, pause, play;

    GamePanel gp;
    Graphics2D g2;
    public Font arial32,letters,arial80B, title,menu,arial20,arial25;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    ArrayList<Integer> dmgNum = new ArrayList<>();
    ArrayList<Integer> dmgCounter = new ArrayList<>();
    ArrayList<Integer> indexList = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; //0 = screen 1, 1 = Screen 2 etc...

    public int slotCol = 0;
    public int slotRow = 0;

    //Stamina Bar
    public int StaminaBarCount = 0;
    public boolean StaminaBarOn = true;
    int subState = 0;

    int counter = 0;


    public UI(GamePanel gp) {
        this.gp = gp;
        arial32 = new Font("Arial", Font.PLAIN, 32);
        arial80B = new Font("Arial",Font.BOLD,80);
        letters = new Font("Calisto MT",Font.BOLD,30);
        title = new Font("Arial",Font.BOLD, 96);
        menu = new Font("Arial", Font.BOLD,48);
        arial20 = new Font("Arial", Font.PLAIN, 20);
        arial25 = new Font("Arial", Font.PLAIN, 25);

        //Create HUD Objects
        Entity heart = new OBJ_Heart(gp);
        heartFull = heart.image;
        heartHalf = heart.image2;
        heartBlank = heart.image3;
        Entity objpause = new OBJ_Pause(gp);
        pause = objpause.image;
        play = objpause.image2;
    }

    public void showMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial32);
        g2.setColor(Color.WHITE);

        //Play state
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawPauseAndPlay();
            if(StaminaBarOn) {
                drawPlayerStamina();
            }
            drawMessage();
            drawDamageNumbers();
            g2.drawImage(playImage, gp.tileSize * 15, 0, gp.tileSize, gp.tileSize, null);
        } //Pause State
        else if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseAndPlay();
            g2.drawImage(pauseImage, gp.tileSize * 15, 0, gp.tileSize, gp.tileSize, null);
        } //DialogueState
        else if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawPauseAndPlay();
            drawDialogueScreen();
        } else if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //Character State
        if(gp.gameState == gp.characterState){
            drawPauseAndPlay();
            drawCharacterScreen();
            drawInvScreen();
        }
        //noteState
        if(gp.gameState == gp.noteState){
            drawPauseAndPlay();
            readNote();
        }

        if(gp.gameState == gp.optionState){
            drawOptionsScreen();
        }
        if(gp.gameState == gp.gameOverState){
            drawEndScreen();
        }
        if(gp.gameState == gp.sleepState){
            drawSleepScreen();
        }

    }

    public void drawTitleScreen() {

        if (titleScreenState == 0) {

            //Background
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //Main Text
            g2.setFont(title);
            String text = "Title";
            int x = getXforCenterScreen(text);
            int y = gp.tileSize * 3;

            //Shadow
            g2.setColor(Color.GRAY);
            g2.drawString(text, x + 5, y + 5);

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            //Character Image
            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down3, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            //Menu
            g2.setFont(menu);
            text = "New Game";
            x = getXforCenterScreen(text);
            y += gp.tileSize * 5;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Load Game";
            x = getXforCenterScreen(text);
            y += gp.tileSize * 1.3;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            text = "Quit";
            x = getXforCenterScreen(text);
            y += gp.tileSize * 1.3;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        } else if (titleScreenState == 1) {
            g2.setColor(Color.WHITE);
            g2.setFont(menu);

            String text = "Select Your Player";
            int x = getXforCenterScreen(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Boy 1";
            x = getXforCenterScreen(text) - gp.tileSize * 3;
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            g2.drawImage(gp.player.down3, x, y + gp.tileSize, gp.tileSize * 2, gp.tileSize * 2, null);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Boy 2";
            x = getXforCenterScreen(text) + gp.tileSize * 3;
            g2.drawString(text, x, y);
            g2.drawImage(gp.player.down2, x, y + gp.tileSize, gp.tileSize * 2, gp.tileSize * 2, null);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Back";
            x = getXforCenterScreen(text);
            y += gp.tileSize * 8;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }

    public void drawDialogueScreen() {
        //Window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color black = new Color(0, 0, 0, 210);
        g2.setColor(black);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenterScreen(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public int getXforRightAlign(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        //draw Blank Heart
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heartBlank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        //reset
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        //Draw current Life
        while (i < gp.player.life) {
            g2.drawImage(heartHalf, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heartFull, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }

    public void drawPlayerStamina(){
        double oneScale = (double)gp.tileSize/gp.player.maxStamina;
        double hpBarValue = oneScale*gp.player.stamina;

        int x = gp.tileSize / 2;
        int y = gp.tileSize * 2;

        g2.setColor(new Color(35,35,35));
        g2.fillRect(x+3, y, gp.tileSize+2, 12);
        g2.setColor(new Color(0,255,239));
        g2.fillRect(x+3, y, (int)hpBarValue, 10);

        StaminaBarCount++;
        if(StaminaBarCount > 600){
            StaminaBarCount = 0;
            StaminaBarOn = false;
        }

    }

    public void drawPauseAndPlay(){
        int x = gp.tileSize * gp.maxScreenCol - gp.tileSize;
        int y = gp.tileSize/2 - gp.tileSize / 3;

        if(gp.gameState == gp.pauseState) {
            g2.drawImage(pause, x, y, null);
        }
        else if(gp.gameState == gp.playState || gp.gameState == gp.dialogueState) {
            g2.drawImage(play, x, y, null);
        }
    }

    public void drawCharacterScreen(){

        final int frameY = gp.tileSize;
        final int frameX = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //text
        g2.setColor(Color.WHITE);
        g2.setFont(arial32);

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 38; //Same as font size

        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("EXP", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);


        //Values
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.life);
        textX = getXforRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.attack);
        textX = getXforRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.defense);
        textX = getXforRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.strength);
        textX = getXforRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.dexterity);
        textX = getXforRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.exp) + "/" + String.valueOf(gp.player.nextLevelExp);
        textX = getXforRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.coin);
        textX = getXforRightAlign(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        if(gp.player.currentWeapon != null) {
            g2.drawImage(gp.player.currentWeapon.icon, tailX - gp.tileSize, textY-14, null);
        }
        textY += lineHeight;
        if(gp.player.currentShield != null) {
            g2.drawImage(gp.player.currentShield.icon, tailX - gp.tileSize, textY, null);
        }
    }

    public void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(arial32);

        for(int i = 0; i < message.size(); i++){
            if(message.get(i) != null){
                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i,counter);
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void getDmgNum(int dmg, int index){
        dmgNum.add(dmg);
        dmgCounter.add(0);
        indexList.add(index);
    }

    public void drawDamageNumbers(){
        g2.setFont(arial32);

        for(int i = dmgNum.size() - 1; i >= 0; i--){ // Iterate backwards to safely remove items
            if(dmgNum.get(i) != null && indexList.get(i) != null && indexList.get(i) < gp.enemies.length){
                Entity enemy = gp.enemies[gp.currentMap][indexList.get(i)];

                // Skip if enemy is null or dead
                if(enemy == null || !enemy.alive) {
                    dmgNum.remove(i);
                    dmgCounter.remove(i);
                    indexList.remove(i);
                    continue;
                }

                // Calculate screen coordinates (same as Entity.draw() method)
                int screenX = enemy.worldX - gp.player.worldX + gp.player.screenX;
                int screenY = enemy.worldY - gp.player.worldY + gp.player.screenY;

                // Offset the damage number above the enemy
                int dmgX = screenX + gp.tileSize/2; // Center horizontally
                int dmgY = screenY - (dmgCounter.get(i) * 2); // Move upward over time

                // Set color with fade effect
                int alpha = Math.max(0, 255 - (dmgCounter.get(i) * 5)); // Fade out over time
                g2.setColor(new Color(255, 0, 0, alpha)); // Red with alpha

                // Draw the damage number
                g2.drawString(Integer.toString(dmgNum.get(i)), dmgX, dmgY);

                // Increment counter
                int counter = dmgCounter.get(i) + 1;
                dmgCounter.set(i, counter);

                // Remove after duration
                if(dmgCounter.get(i) > 60){ // Reduced from 180 for quicker cleanup
                    dmgNum.remove(i);
                    dmgCounter.remove(i);
                    indexList.remove(i);
                }
            } else {
                // Remove invalid entries
                dmgNum.remove(i);
                dmgCounter.remove(i);
                indexList.remove(i);
            }
        }
    }

    public void drawInvScreen() {

        //window parameters
        int frameX = gp.tileSize * 9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //Inv Slots
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 3;

        //Draw Player Items
        for (int i = 0; i < gp.player.inventory.size(); i++) {

            //Equip Cursor
            if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
                    gp.player.inventory.get(i) == gp.player.currentShield ||
                    gp.player.inventory.get(i) == gp.player.currentLight){
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize,gp.tileSize,10,10);
            }


            if (gp.player.inventory.get(i) != null) {
                g2.drawImage(gp.player.inventory.get(i).icon, slotX, slotY, null);
            }

            if(gp.player.inventory.get(i).stackableAmount > 1){
                g2.setFont(arial20);
                int amountX;
                int amountY;

                String s = "" + gp.player.inventory.get(i).stackableAmount;
                amountX = getXforRightAlign(s,slotX + 44);
                amountY = slotY + gp.tileSize;

                //Shadow
                g2.setColor(new Color(60,60,60));
                g2.drawString(s, amountX, amountY);

                //number
                g2.setColor(Color.WHITE);
                g2.drawString(s, amountX-3, amountY-3);
            }

            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
    }

        //Cursor
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //draw cursor
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10,10);


        //descriptions
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize*3;

        //draw description text
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        //for when I add a font
        //g2.setFont()

        int itemIndex = getItemIndex();
        if (itemIndex < gp.player.inventory.size()) {
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

                for (String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
        }

    }

    public int getItemIndex(){
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    public void readNote(){

        int itemIndex = getItemIndex();

            //Window
            int x = gp.tileSize * 2;
            int y = gp.tileSize / 2;
            int width = gp.screenWidth - (gp.tileSize * 4);
            int height = gp.tileSize * 4;

            drawSubWindow(x, y, width, height);

            x += gp.tileSize;
            y += gp.tileSize;

            for (String line : gp.player.inventory.get(itemIndex).note.split("\n")) {
                g2.drawString(line, x, y);
                y += 32;
            }

    }

    public void drawOptionsScreen(){
        g2.setColor(Color.WHITE);
        //add font
        g2.setFont(arial32);

        //Sub Window
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch(subState){
            case 0: optionsTop(frameX, frameY); break;
            case 1: //full screen - will add when full screen is
            case 2: optionsControl(frameX,frameY); break;
            case 3: optionsEndGameConfirmation(frameX,frameY); break;
        }

        gp.keyHandler.enterPressed = false;
    }

    public void optionsTop(int frameX, int frameY){
        int textX;
        int textY;

        String text = "Options";
        textX = getXforCenterScreen(text);
        textY = frameY + gp.tileSize;

        g2.drawString(text, textX, textY);

        //Full Screen on/off
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyHandler.enterPressed){
                if(!gp.fullScreenOn){
                    gp.fullScreenOn = true;
                }
                else if(gp.fullScreenOn){
                    gp.fullScreenOn = false;
                }
            }
        }


        //Music
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
        }

        //SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if(commandNum == 2){
            g2.drawString(">", textX - 25, textY);
        }

        //Control
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyHandler.enterPressed){
                subState = 2;
                commandNum = 0;
            }
        }


        //End Game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 4){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyHandler.enterPressed){
                subState = 3;
                commandNum = 0;
            }
        }

        //Back
        textY += gp.tileSize*2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyHandler.enterPressed){
                commandNum = 0;
                gp.gameState = gp.playState;
            }
        }

        //FullScreen checkbox
        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize*2 + (gp.tileSize/2);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX,textY,gp.tileSize/2,gp.tileSize/2);
        if(gp.fullScreenOn){
            g2.fillRect(textX,textY,gp.tileSize/2,gp.tileSize/2);
        }

        //music checkbox
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 110, gp.tileSize/2); //110/5
        int volumeWidth = 22 * gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,gp.tileSize/2);

        //SE checkbox
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 110, gp.tileSize/2);
        volumeWidth = 22 * gp.se.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,gp.tileSize/2);

        gp.config.saveConfig();
    }

    public void optionsControl(int frameX, int frameY){

        int textX;
        int textY;

        g2.setFont(arial25);

        //Title
        String Text = "Controls";
        textX = getXforCenterScreen(Text);
        textY = frameY + gp.tileSize;
        g2.drawString(Text, textX, textY);

        textX = frameX + (gp.tileSize/2);
        textY += gp.tileSize;
        g2.drawString("Movement", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Inventory", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;
        g2.drawString("Dash", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause",textX, textY); textY += gp.tileSize;

        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("Enter", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;
        g2.drawString("Spacebar", textX, textY); textY += gp.tileSize;
        g2.drawString("P",textX, textY); textY += gp.tileSize;

        textX = frameX + gp.tileSize;
        textY = gp.tileSize*10;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyHandler.enterPressed){
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void optionsEndGameConfirmation(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "Quit game and return \nto the title screen";

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY+=40;
        }

        //Yes or No
        String text = "Yes";
        textX = getXforCenterScreen(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyHandler.enterPressed){
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }

        text = "No";
        textX = getXforCenterScreen(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyHandler.enterPressed){
                subState = 0;
                commandNum = 4;
            }
        }


    }

    public void drawEndScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;

        g2.setFont(title);

        currentDialogue = "You Died";
        //Shadow
        g2.setColor(Color.BLACK);
        x = getXforCenterScreen(currentDialogue);
        y = gp.tileSize*5;

        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
        }

        //Main
        g2.setColor(Color.WHITE);
        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, x - 4, y - 4);
        }

        currentDialogue = "Game Over!";
        //Shadow
        g2.setColor(Color.BLACK);
        x = getXforCenterScreen(currentDialogue);
        y += gp.tileSize*2;

        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
        }

        //Main
        g2.setColor(Color.WHITE);
        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, x - 4, y - 4);
        }


        //UI
        g2.setFont(menu);
        text = "Retry";
        x = getXforCenterScreen(text);
        y += gp.tileSize*3;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x - 40, y);
        }


        //Back to Title Screen
        text = "Quit";
        x = getXforCenterScreen(text);
        y += gp.tileSize*2;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x - 40, y);
        }

    }

    public void drawSleepScreen(){
        counter++;

        if(counter < 120){
            gp.Effects.lighting.filterAlpha += 0.01f;
            if(gp.Effects.lighting.filterAlpha > 1f){
                gp.Effects.lighting.filterAlpha = 1f;
            }
        }
        if(counter >= 120){
            gp.Effects.lighting.filterAlpha -= 0.01f;
            if(gp.Effects.lighting.filterAlpha < 0f){
                gp.Effects.lighting.filterAlpha = 0f;
                counter = 0;
                gp.Effects.lighting.dayState = gp.Effects.lighting.day;
                gp.gameState = gp.playState;
                gp.player.getPlayerImage();
            }
        }

    }

}
