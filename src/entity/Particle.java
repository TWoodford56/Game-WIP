package entity;

import main.GamePanel;

import java.awt.*;

public class Particle extends Entity{


    Entity generator;
    Color color;
    int xd;
    int yd;
    int size;

    public Particle(GamePanel gp, Entity generator, Color color, int speed, int xd, int yd, int size, int maxLife) {
        super(gp);
        this.generator = generator;
        this.color = color;
        this.xd = xd;
        this.yd = yd;
        this.size = size;
        this.maxLife = maxLife;
        this.speed = speed;
        int offset = (gp.tileSize/2) - (size / 2);

        life = maxLife;
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    public void update() {
        worldX += xd * speed;
        worldY += yd * speed;

        life--;

        if(life < maxLife/2){
            yd ++;
        }

        if(life == 0){
            alive = false;
        }

    }

    public void draw(Graphics2D g2d) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        g2d.setColor(color);

        g2d.fillRect(screenX, screenY, size, size);

    }
}
