package entity;


import main.GamePanel;
import main.keyHandler;
import java.awt.*;
import Object.OBJ_woodenSword;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import Object.OBJ_Arrow;
import Object.OBJ_Key;

public class Player extends Entity {

    GamePanel gp;
    public keyHandler kh;

    public final int screenX;
    public final int screenY;

    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int invSize = 20;
    public boolean lightUpdated = false;

    public Player(GamePanel gp, keyHandler kh) {

        super(gp);

        this.gp = gp;
        this.kh = kh;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        bounds = new Rectangle(8,16,32,32 );
        boundsDefaultX = bounds.x;
        boundsDefaultY = bounds.y;

        setDefault();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefault(){
        worldX = gp.tileSize * 75;
        worldY = gp.tileSize * 97;
        speed = 4;
        direction = "up";

        //stats
        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 10;
        coin = 0;
        ammo = 0;
        currentWeapon = new OBJ_woodenSword(gp);
        currentShield = null;
        attack = getAttack();
        defense = getDefense();
//        projectile = new OBJ_Fireball(gp);
        maxMana = 4;
        mana = 4;

        //Life
        maxLife = 6;
        life = maxLife;

        //Stamina
        maxStamina = 10;
        stamina = maxStamina;
    }

    public void setDefaultPosition(){
        worldX = gp.tileSize * 90;
        worldY = gp.tileSize * 98;
        direction = "up";
    }

    public void restorePlayer(){
        life = maxLife;
        invincible = false;
        coin = (coin/3)*2;
    }

    public void setItems(){
        //remove when we begin story element
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(new OBJ_Key(gp));
    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        if(currentWeapon != null) {
            return attack = strength * currentWeapon.attackValue;
        } else {
            return attack = strength;
        }
    }

    public int getDefense(){
        if(currentShield != null) {
            return defense = dexterity * currentShield.defenseValue;
        } else{
            return defense = dexterity;
        }
    }

    public void getPlayerImage(){

            up1 = setup("/Player/boy_up_1", gp.tileSize, gp.tileSize);
            up2 = setup("/Player/boy_up_2",gp.tileSize, gp.tileSize);
            up3 = setup("/Player/boy_up_3", gp.tileSize, gp.tileSize);
            down1 = setup("/Player/boy_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("/Player/boy_down_2", gp.tileSize, gp.tileSize);
            down3 = setup("/Player/boy_down_3", gp.tileSize, gp.tileSize);
            left1 = setup("/Player/boy_left_1", gp.tileSize, gp.tileSize);
            left2 = setup("/Player/boy_left_2", gp.tileSize, gp.tileSize);
            left3 = setup("/Player/boy_left_3", gp.tileSize, gp.tileSize);
            right1 = setup("/Player/boy_right_1", gp.tileSize, gp.tileSize);
            right2 = setup("/Player/boy_right_2", gp.tileSize, gp.tileSize);
            right3 = setup("/Player/boy_right_3", gp.tileSize, gp.tileSize);

    }

    public void update(){

        if(gp.keyHandler.enterPressed && !attacking) {
            attacking = true;
        }

        if(attacking){
            attacking();
        }

        else if(kh.upPressed
                || kh.downPressed
                || kh.leftPressed
                || kh.rightPressed) {

            if (kh.upPressed) {
                direction = "up";
            }
            if (kh.downPressed) {
                direction = "down";
            }
            if (kh.leftPressed) {
                direction = "left";
            }
            if (kh.rightPressed) {
                direction = "right";
            }

            //Check Tile Collision
            collisionOn = false;
            gp.cCheck.checkTile(this);

            //Check Interactive tile collision
            gp.cCheck.checkEntity(this,gp.iTile);

            //Check Obj Collision
            int objIndex = gp.cCheck.checkObject(this, true);
            pickUpObj(objIndex);

            //Check NPC collision
            int NPCIndex = gp.cCheck.checkEntity(this, gp.NPC);
            interactNPC(NPCIndex);

            //Check Enemy collision
            int enemyIndex = gp.cCheck.checkEntity(this,gp.enemies);
            contactEnemy(enemyIndex);

            gp.keyHandler.ePressed = false;

            //If collision is true player cant move
                if(!collisionOn){
                    switch(direction){
                        case "up":
                            worldY -= speed;
                            break;
                        case "down":
                            worldY += speed;
                            break;
                        case "left":
                            worldX -= speed;
                            break;
                        case "right":
                            worldX += speed;
                            break;
                    }
                }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if(projectile != null && currentWeapon.type == type_Bow) {
            //only one at a time need to change
            if (gp.keyHandler.enterPressed && !projectile.alive && shotAvailableCounter == 30 && projectile.haveResource(this)) {

                //set default info
                projectile.set(worldX, worldY, direction, true, this);

                //
                projectile.subtractResource(this);

                //add to list
                gp.projectiles.add(projectile);

                shotAvailableCounter = 0;
            }
        }

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter >= 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(stamina != maxStamina){
            staminaCount++;
            if(staminaCount == 120){
                stamina+=1;
                staminaCount = 0;
            }
        }

        if(tired){
            tiredCounter++;
            if(tiredCounter >= 60){
                tired = false;
                tiredCounter = 0;
            }
        }

        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }

        if(life <= 0){
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
        }
    }

    public void pickUpObj(int index){

        if(index != 999) {

            if(gp.obj[gp.currentMap][index].type == type_obstacle){
                if(gp.keyHandler.ePressed) {
                    gp.obj[gp.currentMap][index].interact();
                }
            }
            //collectable only
            else if (gp.obj[gp.currentMap][index].type == type_collectables) {
                gp.obj[gp.currentMap][index].use(this);
                gp.obj[gp.currentMap][index] = null;
            } else {
                String text;

                if (canObtain(gp.obj[gp.currentMap][index])) {
                    text = "You Picked up " + gp.obj[gp.currentMap][index].name;
                    if (gp.obj[gp.currentMap][index].type == type_ammo) {
                        ammo++;
                    } else if(gp.obj[gp.currentMap][index].name.equals("Key")){
                        hasKey++;
                    }
                } else {
                    text = "Inventory full";
                }
                gp.ui.showMessage(text);
                gp.obj[gp.currentMap][index] = null;
            }
        }

    }

    public void draw(Graphics2D g2d){

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        if(kh.spacePressed){
            dash();
        }

        switch(direction){
            case "up":
                if (!attacking) {
                    if (kh.upPressed) {
                        if (spriteNum == 1) {
                            image = up1;
                        } else if (spriteNum == 2) {
                            image = up2;
                        }
                    } else {
                        image = up3;
                    }
                }
                if(attacking){
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1) {
                        image = attackUp1;
                    } else if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
                break;
            case "down":
                if (!attacking) {
                    if (kh.downPressed) {
                        if (spriteNum == 1) {
                            image = down1;
                        } else if (spriteNum == 2) {
                            image = down2;
                        }
                    } else {
                        image = down3;
                    }
                }
                else if (attacking){
                    if(spriteNum == 1) {
                        image = attackDown1;
                    }
                    else if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if(!attacking) {
                    if (kh.leftPressed) {
                        if (spriteNum == 1) {
                            image = left1;
                        } else if (spriteNum == 2) {
                            image = left2;
                        }
                    } else {
                        image = left3;
                    }
                }
                else if (attacking){
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1) {
                        image = attackLeft1;
                    }
                    else if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if(!attacking) {
                    if (kh.rightPressed) {
                        if (spriteNum == 1) {
                            image = right1;
                        } else if (spriteNum == 2) {
                            image = right2;
                        }
                    } else {
                        image = right3;
                    }
                }
                if(attacking){
                    if(spriteNum == 1) {
                        image = attackRight1;
                    }
                    else if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }
        g2d.drawImage(image, tempScreenX, tempScreenY, null);
    }

    public void interactNPC(int index){
         if(index != 999){
             if(gp.keyHandler.ePressed) {
                 gp.gameState = gp.dialogueState;
                 gp.NPC[gp.currentMap][index].speak();
             }
         }
    }

    public void contactEnemy(int index){
        if(index != 999){
            if(!invincible && !gp.enemies[gp.currentMap][index].dying) {

                int dmg = gp.enemies[gp.currentMap][index].attack - defense;
                if(dmg < 0){ dmg = 0;}
                gp.playSE(3);
                life -= dmg;
                invincible = true;
            }
        }
    }

    public void damageEnemy(int index, int attack){
        if(index != 999){
            if(!gp.enemies[gp.currentMap][index].invincible){

                int dmg = attack - gp.enemies[gp.currentMap][index].defense;
                if(dmg < 0){ dmg = 0;}
                gp.ui.getDmgNum(dmg,index);
                gp.enemies[gp.currentMap][index].life -= dmg;
                gp.enemies[gp.currentMap][index].hit = true;
                gp.playSE(2);
                gp.enemies[gp.currentMap][index].invincible = true;
                damageReaction();

                if(gp.enemies[gp.currentMap][index].life <= 0){
                    gp.enemies[gp.currentMap][index].dying = true;
                    gp.ui.showMessage("You have killed a " + gp.enemies[gp.currentMap][index].name);
                    gp.ui.showMessage("EXP: " + gp.enemies[gp.currentMap][index].exp);
                    exp += gp.enemies[gp.currentMap][index].exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void getPlayerAttackImage(){
        attackUp1 = currentWeapon.attackUp1;
        attackUp2 = currentWeapon.attackUp2;
        attackDown1 = currentWeapon.attackDown1;
        attackDown2 = currentWeapon.attackDown2;
        attackLeft1 = currentWeapon.attackLeft1;
        attackLeft2 = currentWeapon.attackLeft2;
        attackRight1 = currentWeapon.attackRight1;
        attackRight2 = currentWeapon.attackRight2;
    }

    public void attacking(){
        spriteCounter++;

        if(spriteCounter <= 5){
            spriteNum = 1;
        } else if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;

            //Save current info
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int boundsWidth = bounds.width;
            int boundsHeight = bounds.height;

            //Adjust player info for attack area
            switch(direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            //Attack Area becomes solid area
            bounds.width = attackArea.width;
            bounds.height = attackArea.height;
            //Check enemy collision
            int monsterIndex = gp.cCheck.checkEntity(this,gp.enemies);
            damageEnemy(monsterIndex, attack);

            int iTileindex = gp.cCheck.checkEntity(this,gp.iTile);
            damageInteractivetile(iTileindex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            bounds.width = boundsWidth;
            bounds.height = boundsHeight;

        } else if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void dash(){

        if(!tired && stamina >= 2) {
            // Store original position
            int originalX = worldX;
            int originalY = worldY;

            // Calculate new position based on direction
            switch (direction) {
                case "up":
                    worldY -= gp.tileSize/3;
                    break;
                case "down":
                    worldY += gp.tileSize/3;
                    break;
                case "left":
                    worldX -= gp.tileSize/3;
                    break;
                case "right":
                    worldX += gp.tileSize/3;
                    break;
            }

            // Check collision at new position
            collisionOn = false;
            gp.cCheck.checkTile(this);

            // Check object collision
            int objIndex = gp.cCheck.checkObject(this, true);

            // If collision detected, revert to original position
            if (collisionOn || (objIndex != 999 && gp.obj[gp.currentMap][objIndex].collision)) {
                worldX = originalX;
                worldY = originalY;
            }
            stamina-=2;
            tired = true;
        }
        gp.ui.StaminaBarOn = true;
    }

    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp += nextLevelExp;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are now Level " + level + "\n" + "You're strength is growing";


        }
    }

    public void selectItem(){
        int itemIndex = gp.ui.getItemIndex();

        if(itemIndex < inventory.size()){
            Entity selection = inventory.get(itemIndex);

            if(selection.type == type_sword){
                currentWeapon = selection;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selection.type == type_axe){
                currentWeapon = selection;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selection.type == type_shield){
                currentShield = selection;
                defense = getDefense();
            }
            if(selection.type == type_consumable){
                selection.use(this);
                reduceConsumables(itemIndex);
            }
            if(selection.type == type_note){
                gp.gameState = gp.noteState;
            }
            if(selection.type == type_Bow){
                currentWeapon = selection;
                projectile = new OBJ_Arrow(gp);
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selection.type == type_light){
                if(currentLight == selection){
                    currentLight = null;
                }
                else{
                    currentLight = selection;
                }
                lightUpdated = true;
            }
        }
    }

    public void damageInteractivetile(int index){

        if(index != 999 && gp.iTile[gp.currentMap][index].destructible && gp.iTile[gp.currentMap][index].correctWeapon(this) && !gp.iTile[gp.currentMap][index].invincible) {

            gp.iTile[gp.currentMap][index].life--;
            gp.iTile[gp.currentMap][index].invincible = true;
            generateParticle(gp.iTile[gp.currentMap][index], gp.iTile[gp.currentMap][index]);
            if (gp.iTile[gp.currentMap][index].life == 0) {
                gp.iTile[gp.currentMap][index] = gp.iTile[gp.currentMap][index].getDestroyedForm();
            }
        }

    }

    public int searchItem(String itemName){
        int i = 0;
        int index = 999;
        while(i < gp.player.inventory.size()){
            if(gp.player.inventory.get(i).name.equals(itemName)){
                index = i;
            }
            i++;
        }
        return index;
    }

    public boolean canObtain(Entity item){
        boolean canObtain = false;

        if(item.stackable){
            int index = searchItem(item.name);
            if(index != 999){
                inventory.get(index).stackableAmount++;
                canObtain = true;
            } else {//new item needs to check space
                if (inventory.size() < invSize) {
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        else {
            if (inventory.size() < invSize) {
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }

    public void reduceConsumables(int itemIndex){
        Entity stack = inventory.get(itemIndex);
        if(stack.stackableAmount > 1){
            stack.stackableAmount--;
        } else {
            inventory.remove(itemIndex);
        }

    }

    public void sleepingImage(BufferedImage image){
        up1 = image;
        up2 = image;
        up3 = image;
        down1 = image;
        down2 = image;
        down3 = image;
        left1 = image;
        left2 = image;
        left3 = image;
        right1 = image;
        right2 = image;
        right3 = image;
    }

}
