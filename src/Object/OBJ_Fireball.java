package Object;

import main.GamePanel;
import entity.Projectile;

public class OBJ_Fireball extends Projectile{

    GamePanel gp;

    public OBJ_Fireball(GamePanel gp){
        super(gp);
        this.gp =gp;

        name = "Fireball";
        maxLife = 80;
        life = maxLife;
        speed = 8;
        attack = 2;
        alive = false;
        icon = setup("/Projectile/fireball_right_1", gp.tileSize,gp.tileSize);
        getImage();

    }

    public void getImage(){
        up1 = setup("/Projectile/fireball_up_1", gp.tileSize,gp.tileSize);
        down1 = setup("/Projectile/fireball_down_1", gp.tileSize,gp.tileSize);
        right1 = setup("/Projectile/fireball_right_1", gp.tileSize,gp.tileSize);
        left1 = setup("/Projectile/fireball_left_1", gp.tileSize,gp.tileSize);
        //Temporary
        up2 = setup("/Projectile/fireball_up_2", gp.tileSize,gp.tileSize);
        down2 = setup("/Projectile/fireball_down_2", gp.tileSize,gp.tileSize);
        right2 = setup("/Projectile/fireball_right_2", gp.tileSize,gp.tileSize);
        left2 = setup("/Projectile/fireball_left_2", gp.tileSize,gp.tileSize);
    }
}
