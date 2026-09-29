package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager {

    GamePanel gp;
    BufferedImage worldMap[];
    public boolean miniMapOn = false;

    public Map(GamePanel gp) {
        super(gp);
        this.gp = gp;
        createWorldMap();
    }

    public void createWorldMap() {

        worldMap = new BufferedImage[gp.maxMap];
        int worldMapWidth = gp.tileSize*gp.maxWorldCol;
        int worldMapHeight = gp.tileSize*gp.maxWorldRow;
        for(int i = 0; i < gp.maxMap; i++) {
            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = worldMap[i].createGraphics();

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                //must change to i
                int tileNum = mapTileNum[gp.currentMap][col][row];
                int x = gp.tileSize * col;
                int y = gp.tileSize * row;
                g2d.drawImage(tile[tileNum].image, x, y, null);
                col++;

                if (col == gp.maxWorldCol) {
                    row++;
                    col = 0;
                }
            }
        }
    }

    public void drawFullMapScreen(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, gp.tileSize, gp.tileSize);

        int width = 500;
        int height = 500;
        int x = gp.screenWidth/2 - width/2;
        int y = gp.screenHeight/2 - height/2;
        g2d.drawImage(worldMap[gp.currentMap], x, y, width,height,null);

        //draw player
        double scale = (double) (gp.tileSize*gp.maxWorldCol)/width;
        int playerX = (int)(x + gp.player.worldX/scale);
        int playerY = (int)(y + gp.player.worldY/scale);
        int playerSize = (int)(gp.tileSize/scale);
        g2d.drawImage(gp.player.down3,playerX,playerY,playerSize,playerSize,null);

        g2d.setFont(gp.ui.arial32);
        g2d.setColor(Color.WHITE);
        g2d.drawString("M or Esc to Exit",675,700);
    }

    public void drawMiniMap(Graphics2D g2d) {
        if(miniMapOn) {
            int width = 200;
            int height = 200;
            int x = gp.screenWidth - width - 50;
            int y = 50;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2d.drawImage(worldMap[gp.currentMap], x, y, width,height,null);

            double scale = (double) (gp.tileSize*gp.maxWorldCol)/width;
            int playerX = (int)(x + gp.player.worldX/scale);
            int playerY = (int)(y + gp.player.worldY/scale);
            int playerSize = (int)(gp.tileSize/3);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g2d.drawImage(gp.player.down3,playerX-6,playerY-6,playerSize,playerSize,null);
        }
    }
}
