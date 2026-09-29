package Interactive_Tiles;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class InteractiveTile extends Entity {

    GamePanel gp;

    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    public void update(){
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter >= 30){ // 30 frames = 0.5 seconds at 60 FPS
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public boolean correctWeapon(Entity e) {
        boolean isCorrect = false;
        return isCorrect;
    }

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }


}
