package EnvironmentalEffects;

import main.GamePanel;

import java.awt.*;

public class EffectsManager {

    public Lighting lighting;
    GamePanel gp;

    public EffectsManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setUp(){
        lighting = new Lighting(gp);
    }

    public void update(){
        lighting.update();
    }

    public void draw(Graphics2D g2d){
        lighting.draw(g2d);
    }
}
