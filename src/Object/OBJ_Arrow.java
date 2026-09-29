package Object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Arrow extends Projectile {

    GamePanel gp;

    public OBJ_Arrow(GamePanel gp){
        super(gp);
        this.gp =gp;

        stackable = true;
        name = "Basic Arrow";
        maxLife = 80;
        life = maxLife;
        speed = 10;
        attack = 2;
        alive = false;
        useCost = 1;
        type = type_ammo;
        icon = setup("/Projectile/Arrowright1", gp.tileSize,gp.tileSize);
        getImage();

    }

    public void getImage(){
        up1 = setup("/Projectile/Arrowup1", gp.tileSize,gp.tileSize);
        down1 = setup("/Projectile/Arrowdown1", gp.tileSize,gp.tileSize);
        right1 = setup("/Projectile/Arrowright1", gp.tileSize,gp.tileSize);
        left1 = setup("/Projectile/Arrowleft1", gp.tileSize,gp.tileSize);
        //Temporary
        up2 = setup("/Projectile/Arrowup1", gp.tileSize,gp.tileSize);
        down2 = setup("/Projectile/Arrowdown1", gp.tileSize,gp.tileSize);
        right2 = setup("/Projectile/Arrowright1", gp.tileSize,gp.tileSize);
        left2 = setup("/Projectile/Arrowleft1", gp.tileSize,gp.tileSize);
    }

    public boolean haveResource(Entity user){
        boolean haveResource = false;
        if(user.ammo >= useCost){
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user){
        user.ammo -= useCost;
        gp.player.reduceConsumables(gp.player.searchItem("Basic Arrow"));
    }

    public Color getParticleColor(){
        Color color = Color.red;
        return color;
    }

    public int getParticleSize(){
        int size = 8;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 2;
        return speed;
    }

    public int getParticelMaxLife(){
        int maxLife = 8;
        return maxLife;
    }
}
