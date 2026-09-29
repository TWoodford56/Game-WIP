package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;
    public Projectile projectile;

    public int worldX, worldY;
    public int speed;
    public int stamina;
    public int maxStamina;
    public int staminaCount;
    public boolean tired;
    public int tiredCounter;

    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2, damaged;
    public BufferedImage icon;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public Rectangle bounds = new Rectangle(0,0,48,48);
    public int boundsDefaultX, boundsDefaultY;
    public boolean collisionOn = false;

    //NPC Movement
    public int actionLock;

    //Player attacking
    boolean attacking = false;
    boolean hit = false;
    int hitTimer = 0;

    //Dialogues
    String dialogues[] = new String[20];
    int dialogueIndex = 0;

    //Character Status
    public int maxLife;
    public int life;
    public boolean alive = true;
    public boolean dying = false;
    int dyingCount = 0;
    boolean hpBarOn = false;
    int hpBarCount = 0;
    public boolean onTrack = false;
    boolean stop = false;
    int stopCounter = 0;

    //OBJ stuff for render order
    public BufferedImage image, image2, image3;
    public String name;
    public boolean activated = false;
    public boolean collision = false;

    //Enemies and damage helpers
    public boolean invincible = false;
    public int invincibleCounter = 0;

    //Character stats
    public int strength;
    public int level;
    public int exp;
    public int dexterity;
    public int attack;
    public int defense;
    public int nextLevelExp;
    public int coin;
    public int ammo;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;

    //item attributes
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public String note = null;
    public boolean stackable = false;
    public int stackableAmount = 1;
    public int lightRadius;

    //Type

    public int type;
    public final int type_player = 0;
    public final int type_Enemy = 1;
    public final int type_npc = 2;
    public final int type_sword = 3;
    public final int type_shield = 4;
    public final int type_consumable = 5;
    public final int type_note = 6;
    public final int type_Bow = 7;
    public final int type_ammo = 8;
    public final int type_collectables = 9;
    public final int type_axe = 10;
    public final int type_obstacle = 11;
    public final int type_key = 12;
    public final int type_light = 13;

    //If i ever decide to do spells
    public int maxMana;
    public int mana;
    public int useCost;
    public int shotAvailableCounter = 0;

    //Keys
    public boolean found;
    public int hasKey = 1;


    public Entity(GamePanel gp) {
        this.gp = gp;

        bounds = new Rectangle(0, 0, 48, 48);
        boundsDefaultX = bounds.x;
        boundsDefaultY = bounds.y;
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(screenX < -gp.tileSize || screenX > gp.screenWidth + gp.tileSize || screenY < -gp.tileSize || screenY > gp.screenHeight + gp.tileSize){
            return;
        }


        BufferedImage img = null;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
        {

            if(!hit) {
                switch (direction) {
                    case "up":
                        if (spriteNum == 1) {
                            img = up1;
                        } else if (spriteNum == 2) {
                            img = up2;
                        }
                        break;
                    case "down":
                        if (spriteNum == 1) {
                            img = down1;
                        } else if (spriteNum == 2) {
                            img = down2;
                        }
                        break;
                    case "left":
                        if (spriteNum == 1) {
                            img = left1;
                        } else if (spriteNum == 2) {
                            img = left2;
                        }
                        break;
                    case "right":
                        if (spriteNum == 1) {
                            img = right1;
                        } else if (spriteNum == 2) {
                            img = right2;
                        }
                        break;
                }
            }
            else if(hit){
                img = damaged;
                hpBarOn = true;
                hpBarCount = 0;
            }
            if(name != null && name.equals("Spikes")){
                if(!activated){
                    img = image;
                } else if(activated) {
                    img = image2;
                }
            }

            //monster hp bar
            if(type == type_Enemy && hpBarOn) {
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                hpBarCount++;
                if(hpBarCount > 600){
                    hpBarCount = 0;
                    hpBarOn = false;
                }
            }

            if(dying){
                dyingAnimation(g2);
            }
            g2.drawImage(img, screenX, screenY, null);
        }
    }

    public BufferedImage setup(String imagePath, int width, int height){

        UtilityTool UTool = new UtilityTool();
        BufferedImage ScaledImage = null;

        try{
            ScaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            ScaledImage = UTool.scaleImage(ScaledImage, width, height);
        }catch(IOException e){
            e.printStackTrace();
        }

        return ScaledImage;

    }

    public void damagePlayer(int attack){
        if(!gp.player.invincible){
            int dmg = attack - gp.player.defense;
            if(dmg < 0){ dmg = 0;}
            gp.player.life -= dmg;
            gp.player.invincible = true;
        }
    }

    public void checkDrop(){}

    public void dropItem(Entity droppedItem){
        for(int i = 0; i < gp.obj[1].length; i++){
            if(gp.obj[gp.currentMap][i] == null){
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX; //dead enemies
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public void setAction(){}
    public void damageReaction(){}
    public void speak(){
        if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void use(Entity e){}

    public void update(){

        setAction();
        checkCollision();

        //If collision is true NPC can't move
        if(collisionOn == false){
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

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter >= 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }

        //can change for different scenarios
        if(stop){
            stopCounter++;
            if(stopCounter >= 120){
                stop = false;
                stopCounter = 0;
            }
        }

        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }

        if(hit){
            hitTimer++;
            if(hitTimer >= 20){
                hit = false;
                hitTimer = 0;
            }
        }
    }

    public void dyingAnimation(Graphics2D g2){
        dyingCount++;

        int i = 8;

        if(dyingCount <= i){
            changeAlpha(g2,0f);
        }
        if(dyingCount > i && dyingCount <= i*2){
            changeAlpha(g2,1f);
        }
        if(dyingCount > i*2 && dyingCount <= i*3){
            changeAlpha(g2,0f);
        }
        if(dyingCount > i*3 && dyingCount <= i*4){
            changeAlpha(g2,1f);
        }
        if(dyingCount > i*4 && dyingCount <= i*5){
            changeAlpha(g2,0f);
        }
        if(dyingCount > i*5 && dyingCount <= i*6){
            changeAlpha(g2,1f);
        }

        if (dyingCount > i*6){
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alpha){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public Color getParticleColor(){
        Color color = new Color(60,40,30);
        return color;
    }

    public int getParticleSize(){
        int size = 6;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }

    public int getParticelMaxLife(){
        int maxLife = 20;
        return maxLife;
    }

    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed  = generator.getParticleSpeed();
        int maxLife = generator.getParticelMaxLife();

        //particle generator xd and yd is the direction the particle moves
        Particle particle1 = new Particle(gp,target,color,speed,-2,-1,size,maxLife);
        Particle particle2 = new Particle(gp,target,color,speed,2,-1,size,maxLife);
        Particle particle3 = new Particle(gp,target,color,speed,-2,1,size,maxLife);
        Particle particle4 = new Particle(gp,target,color,speed,2,1,size,maxLife);
        gp.particles.add(particle1);
        gp.particles.add(particle2);
        gp.particles.add(particle3);
        gp.particles.add(particle4);
    }

    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + bounds.x)/gp.tileSize;
        int startRow = (worldY + bounds.y)/gp.tileSize;

        gp.pFinding.setNode(startCol,startRow,goalCol,goalRow);

        if(gp.pFinding.search()){
            int nextX = gp.pFinding.pathList.get(0).col*gp.tileSize;
            int nextY = gp.pFinding.pathList.get(0).row*gp.tileSize;

            int entityLeft = worldX + bounds.x;
            int entityRight = worldX + bounds.x + bounds.width;
            int entityTop = worldY + bounds.y;
            int entityBottom = worldY + bounds.y + bounds.height;

            if(entityTop > nextY && entityLeft >= nextX && entityRight < nextX + gp.tileSize){
                direction = "up";
                checkCollision();
            } else if(entityTop < nextY && entityLeft >= nextX && entityRight < nextX + gp.tileSize){
                direction = "down";
                checkCollision();
            } else if(entityTop >= nextY && entityBottom < nextY + gp.tileSize){
                if(entityLeft > nextX){
                    direction = "left";
                    checkCollision();
                } else if (entityLeft < nextX){
                    direction = "right";
                    checkCollision();
                }
            } else if(entityTop > nextY && entityLeft > nextX){
                direction = "up";
                checkCollision();
                if(collisionOn){
                    direction = "Left";
                }
            } else if(entityTop > nextY && entityLeft < nextX){
                direction = "up";
                checkCollision();
                if(collisionOn){
                    direction = "Right";
                }
            } else if(entityTop < nextY && entityLeft > nextX){
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "Left";
                }
            } else if(entityTop < nextY && entityLeft < nextX){
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "Right";
                }
            }
            int nextCol = gp.pFinding.pathList.get(0).col;
            int nextRow = gp.pFinding.pathList.get(0).row;

            //Disable if we want the entity to follow the player
            if(nextCol == goalCol && nextRow == goalRow){
                onTrack = false;
                stop = true;

            }

        }
    }

    public void checkCollision(){
        collisionOn = false;

        gp.cCheck.checkTile(this);
        gp.cCheck.checkObject(this, false);
        boolean contactPlayer = gp.cCheck.checkPlayer(this);
        gp.cCheck.checkEntity(this,gp.NPC);
        gp.cCheck.checkEntity(this,gp.enemies);
        gp.cCheck.checkEntity(this,gp.iTile);

        if(this.type == type_Enemy && contactPlayer){
            damagePlayer(attack);
        }
    }

    public void interact(){}

}
