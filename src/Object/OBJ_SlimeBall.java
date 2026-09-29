package Object;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_SlimeBall extends Projectile {

    GamePanel gp;

    public OBJ_SlimeBall(GamePanel gp){
        super(gp);
        this.gp =gp;

        name = "Slime Ball";
        maxLife = 80;
        life = maxLife;
        speed = 6;
        attack = 2;
        alive = false;
        icon = setup("/Projectile/SlimeBall", gp.tileSize,gp.tileSize);
        getImage();

    }

    public void getImage(){
        up1 = setup("/Projectile/SlimeBall", gp.tileSize,gp.tileSize);
        up2 = up1;
        down1 = up1;
        down2 = up1;
        left1 = up1;
        left2 = up1;
        right1 = up1;
        right2 = up1;
    }

    public Color getParticleColor(){
        Color color = Color.green;
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
