package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[100];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/Maps/map1.txt",0);
        loadMap("/Maps/HouseInterior1.txt",1);
    }

    public void getTileImage() {

            setup(10,"Grass1", false);

            setup(11,"Grass2", false);

            setup(12,"Grass3", false);

            //consider changing collision
            setup(13,"Grass&Rock",false);

            setup(14,"HorizontalWater", true);

            setup(15,"VerticalWater", true);

            //Need to potentially work on collision

            setup(16,"RiverBottom", true);

            setup(17,"RiverTop", true);

            setup(18,"RiverRight", true);

            setup(19,"RiverLeft", true);

            setup(20,"RiverOuterCornerTL", true);

            setup(21,"RiverOuterCornerTR", true);

            setup(22,"RiverOuterCornerBL", true);

            setup(23,"RiverOuterCornerBR", true);

            setup(24,"RiverInnerCornerTL", true);

            setup(25,"RiverInnerCornerTR", true);

            setup(26,"RiverInnerCornerBL", true);

            setup(27,"RiverInnerCornerBR", true);


            setup(28, "HouseP1",true);
            setup(29, "HouseP2",true);
            setup(30, "HouseP3",true);
            setup(31, "HouseP4",true);
            setup(32, "HouseP5",true);
            setup(33, "HouseP6",true);
            setup(34, "HouseP7",true);
            setup(35, "HouseP8",true);
            setup(36, "HouseP9",true);
            setup(37, "HouseP10",false);
            setup(38, "HouseP11",false);
            setup(39, "HouseP12",true);
            setup(40, "HouseP13",false);
            setup(41, "HouseP14",false);
            setup(42, "HouseP15",false);
            setup(43, "HouseP16",false);

            setup(44,"Wall",true);
            setup(45,"HouseInteriorWall",true);
            setup(46,"HouseInteriorFloor",false);
            setup(47,"HouseInteriorCupboard",true);
            setup(48,"HouseInteriorSideTable",true);
            setup(49,"HouseInteriorDoor",false);
            setup(50,"HouseInteriorChairDown",true);
            setup(51,"HouseInteriorChairRight",true);
            setup(52,"HouseInteriorChairLeft",true);
            setup(53,"HouseInteriorChairUp",true);
            setup(54,"HouseInteriorRedBed",true);
            setup(55,"HouseInteriorTable",true);




    }

    public void setup(int index, String imagePath, boolean collision){
        UtilityTool UTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/Environment/" + imagePath + ".png"));
            tile[index].image = UTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

            int tileNum = mapTileNum[gp.currentMap][col][row];

            int worldX = col * gp.tileSize;
            int worldY = row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                    && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                    && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                    && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                        g2.drawImage(tile[tileNum].image, screenX, screenY,null);
            }
            col++;

            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    public void loadMap(String filePath, int map){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
