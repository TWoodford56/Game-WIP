package EnvironmentalEffects;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gp;

    BufferedImage darknessFilter;
    int dayCounter = 0;
    public float filterAlpha;

    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;

    float dayAlpha = (float)(0.3/28800);
    float dawnAlpha = (float)(0.58/14400);

    public Lighting(GamePanel gp) {
        this.gp = gp;
        setLightSource();
    }

    public void draw(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2d.drawImage(darknessFilter,0,0,null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void setLightSource(){
        darknessFilter = new BufferedImage(gp.screenWidth,gp.screenHeight,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) darknessFilter.getGraphics();

        if(gp.player.currentLight == null){
            g2d.setColor(new Color(0,0,0,0.88f));
        }
        else {

            int centerX = gp.player.screenX + (gp.tileSize / 2);
            int centerY = gp.player.screenY + (gp.tileSize / 2);

            //Gradual shift to darkness effect
            Color dawnColor[] = new Color[7];
            Color duskColor[] = new Color[7];
            float fraction[] = new float[7];

            duskColor[0] = new Color(0, 0, 0.1f, 0f);
            duskColor[1] = new Color(0, 0, 0.1f, 0.2f);
            duskColor[2] = new Color(0, 0, 0.1f, 0.4f);
            duskColor[3] = new Color(0, 0, 0.1f, 0.6f);
            duskColor[4] = new Color(0, 0, 0.1f, 0.8f);
            duskColor[5] = new Color(0, 0, 0.1f, 0.85f);
            duskColor[6] = new Color(0, 0, 0.1f, 0.88f);

            dawnColor[0] = new Color(0.1f, 0.1f, 0, 0f);
            dawnColor[1] = new Color(0.1f, 0.1f, 0, 0.2f);
            dawnColor[2] = new Color(0.1f, 0.1f, 0, 0.4f);
            dawnColor[3] = new Color(0.1f, 0.1f, 0, 0.6f);
            dawnColor[4] = new Color(0.1f, 0.1f, 0, 0.8f);
            dawnColor[5] = new Color(0.1f, 0.1f, 0, 0.85f);
            dawnColor[6] = new Color(0.1f, 0.1f, 0, 0.88f);

            fraction[0] = 0f;
            fraction[1] = 0.2f;
            fraction[2] = 0.4f;
            fraction[3] = 0.6f;
            fraction[4] = 0.8f;
            fraction[5] = 0.9f;
            fraction[6] = 1.0f;

            if(dayState == day || dayState == dawn) {
                RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius, fraction, dawnColor);
                g2d.setPaint(gPaint);
            }
            if(dayState == night || dayState == dusk) {
                RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius, fraction, duskColor);
                g2d.setPaint(gPaint);
            }
        }
        g2d.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2d.dispose();
    }

    public void update(){
        dayCounter++;
            switch(dayState){
                case day:
                    if(dayCounter >= 28800){
                        dayState = dusk;
                        dayCounter = 0;
                    } else{
                        if(filterAlpha + dayAlpha >= 1) {
                            filterAlpha = 1;
                        }
                        else{
                            filterAlpha += dayAlpha;
                        }
                    }
                    break;
                case dusk:
                    if(dayCounter >= 14400) {
                        dayState = night;
                        dayCounter = 0;
                    } else {
                        if(filterAlpha + dawnAlpha >= 1) {
                            filterAlpha = 1;
                        }
                        else{
                            filterAlpha += dawnAlpha;
                        }
                    }
                    break;
                case night:
                    if(dayCounter >= 28800) {
                        dayState = dawn;
                        dayCounter = 0;
                    } else{
                        if(filterAlpha - dayAlpha <= 0){
                            filterAlpha = 0;
                        } else {
                            filterAlpha -= dayAlpha;
                        }
                    }
                    break;
                case dawn:
                    if(dayCounter >= 14400){
                        dayState = day;
                        dayCounter = 0;
                    } else{
                        if(filterAlpha - dawnAlpha <= 0){
                            filterAlpha = 0;
                        } else {
                            filterAlpha -= dawnAlpha;
                        }
                    }
                    break;
            }

        if(gp.player.lightUpdated == true){
            setLightSource();
            gp.player.lightUpdated = false;
        }
    }

}
