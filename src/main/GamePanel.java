package main;

import EnvironmentalEffects.EffectsManager;
import Interactive_Tiles.InteractiveTile;
import Pathfinding.Pathfinding;
import entity.Entity;
import entity.Player;
import tile.Map;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings
    final int originalTileSize = 16; //16x16
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48
    public final int maxScreenCol = 20; //960
    public final int maxScreenRow = 16; //768
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public boolean fullScreenOn = false;


    public EffectsManager Effects = new EffectsManager(this);
    public EventHandler eHandler = new EventHandler(this);
    public keyHandler keyHandler = new keyHandler(this);
    Thread gameThread;
    public Player player = new Player(this,keyHandler);
    Sound se = new Sound();
    Sound music = new Sound();
    public UI ui = new UI(this);
    public CollisionCheck cCheck = new CollisionCheck(this);
    public TileManager tm = new TileManager(this);
    Config config = new Config(this);
    public final int maxMap = 20;
    public int currentMap = 0;
    public Pathfinding pFinding = new Pathfinding(this);
    Map map = new Map(this);

    //We can display up to 10 Objects at the same time
    public Entity obj[][] = new Entity[maxMap][50];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][500];
    public AssetSetter ASetter = new AssetSetter(this);
    public Entity NPC[][] = new Entity[maxMap][50];
    public Entity enemies[][] = new Entity[maxMap][20];
    ArrayList<Entity> entities = new ArrayList<>(100);
    public ArrayList<Entity> projectiles = new ArrayList<>();
    public ArrayList<Entity> particles = new ArrayList<>();

    //sort
    private static final Comparator<Entity> ENTITY_Y_COMPARATOR =
            (e1, e2) -> Integer.compare(e1.worldY, e2.worldY);

    //FPS
    int FPS = 60;

    //Timer
    GameTimer gameTimer = new GameTimer(this);

    //World Settings
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;

    //Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int noteState = 5;
    public final int optionState = 6;
    public final int gameOverState = 7;
    public final int sleepState = 8;
    public final int mapState = 9;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame(){
        ASetter.setObject();
        ASetter.setNPC();
        ASetter.setEnemies();
        ASetter.setInteractiveTiles();
        Effects.setUp();
        playMusic(0);
        gameState = titleState;

//        if(fullScreenOn){
//            setFullScreen();
//        }
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null){

            //update info
            update();
            //draw the screen with the updated info
            paintImmediately(0, 0, getWidth(), getHeight());

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //Debug
        long drawStart = 0;
        if(keyHandler.checkDebugInfo){
            drawStart = System.nanoTime();
        }

        //Title Screen
        if(gameState == titleState){
            ui.draw(g2d);
        }
        //map
        else if(gameState == mapState){
            map.drawFullMapScreen(g2d);
        }


        else{

            //Tile
            tm.draw(g2d);

            //interactive tiles
            for(int i = 0; i < iTile[1].length; i++){
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].draw(g2d);
                }
            }

            //populate entities list
            entities.add(player);
            for(int i = 0; i < NPC[1].length; i++){
                if(NPC[currentMap][i] != null){
                    entities.add(NPC[currentMap][i]);
                }
            }
            for(int i = 0; i < enemies[1].length; i++){
                if(enemies[currentMap][i] != null){
                    entities.add(enemies[currentMap][i]);
                }
            }
            for(int i = 0; i < projectiles.size(); i++){
                if(projectiles.get(i) != null){
                    entities.add(projectiles.get(i));
                }
            }
            for(int i = 0; i < particles.size(); i++){
                if(particles.get(i) != null){
                    entities.add(particles.get(i));
                }
            }
            for(int i = 0; i < obj[1].length; i++){
                if(obj[currentMap][i] != null){
                    entities.add(obj[currentMap][i]);
                }
            }

                //sort
            Collections.sort(entities, ENTITY_Y_COMPARATOR);

            //draw
            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).draw(g2d);
            }
            entities.clear();

            //do before UI so ui is not covered
            Effects.draw(g2d);

            map.drawMiniMap(g2d);

            //UI
            ui.draw(g2d);

            if(keyHandler.checkDebugInfo) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2d.setColor(Color.BLACK);
                g2d.drawString("Draw time: " + passed, 10, 400);
                System.out.println("Draw Time" + passed);
            }
        }
        g2d.dispose();
    }

    public void retry(){
        player.setDefaultPosition();
        player.restorePlayer();
        ASetter.setEnemies();
        ASetter.setNPC();
    }

    public void restart(){
        player.setDefault();
        player.setItems();
        ASetter.setEnemies();
        ASetter.setNPC();
        ASetter.setInteractiveTiles();
        ASetter.setObject();

    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }




    public void update(){
        gameTimer.update();
        if(gameState == playState) {
            player.update();
            eHandler.checkEvent();
            for(int i = 0; i < NPC[1].length; i++) {
                if (NPC[currentMap][i] != null) {
                    NPC[currentMap][i].update();
                }
            }
            for(int i = 0; i < enemies[1].length; i++){
                if(enemies[currentMap][i] != null) {
                    if(enemies[currentMap][i].alive && !enemies[currentMap][i].dying) {
                        enemies[currentMap][i].update();
                    }
                    else if(!enemies[currentMap][i].alive){
                        enemies[currentMap][i].checkDrop();
                        enemies[currentMap][i] = null;
                    }
                }
            }

            for(int i = 0; i < projectiles.size(); i++){
                if(projectiles.get(i) != null) {
                    if(projectiles.get(i).alive) {
                        projectiles.get(i).update();
                    }
                    else if(!projectiles.get(i).alive){
                        projectiles.remove(i);
                    }
                }
            }

            for(int i = 0; i < particles.size(); i++){
                if(particles.get(i) != null) {
                    if(particles.get(i).alive) {
                        particles.get(i).update();
                    }
                    else if(!particles.get(i).alive){
                        particles.remove(i);
                    }
                }
            }

            for(int i = 0; i < iTile[1].length; i++){
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }

            Effects.update();

        }
        if(gameState == pauseState){
            //No Player info is updated during the pause
        }
    }

}
