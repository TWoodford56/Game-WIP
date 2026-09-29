package entity;

import main.GamePanel;

public class Projectile extends Entity{

    Entity user;

    public Projectile(GamePanel gp){
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = maxLife;
    }
    public void update(){

        if (user == gp.player) {
            int enemyIndex = gp.cCheck.checkEntity(this,gp.enemies);
            if(enemyIndex != 999){
                gp.player.damageEnemy(enemyIndex, attack);
                generateParticle(user.projectile,gp.enemies[gp.currentMap][enemyIndex]);
                alive = false;
            }

        }
        if(user != gp.player){
            boolean contactPlayer = gp.cCheck.checkPlayer(this);
            if(!gp.player.invincible && contactPlayer){
                damagePlayer(attack);
                generateParticle(user.projectile,gp.player);
                alive = false;
            }
        }

        switch(direction){
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        life--;
        if(life <= 0){
            alive = false;
        }

        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

    }

    public boolean haveResource(Entity user){
        return false;
    }

    public void subtractResource(Entity user){}

}
