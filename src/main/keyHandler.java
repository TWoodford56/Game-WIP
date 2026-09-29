package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, ePressed, enterPressed, spacePressed, fPressed;
// Debug
    public boolean checkDebugInfo = false;

    public keyHandler(GamePanel gp){
        this.gp = gp;
    }


    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){

        int code = e.getKeyCode();

        if(gp.gameState == gp.titleState){
            titleState(code);
        }
        else if(gp.gameState == gp.playState){
            playState(code);
        }
        else if(gp.gameState == gp.pauseState){
            pauseState(code);
        }
        else if(gp.gameState == gp.dialogueState){
            dialogueState(code);
        }
        else if(gp.gameState == gp.characterState){
            characterState(code);
        }
        else if(gp.gameState == gp.noteState){
            noteState(code);
        }
        else if(gp.gameState == gp.optionState){
            optionState(code);
        }
        else if(gp.gameState == gp.gameOverState){
            gameOverState(code);
        }
        else if(gp.gameState == gp.mapState){
            mapState(code);
        }

    }

    public void gameOverState(int code){
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            if(gp.ui.commandNum < 1) {
                gp.ui.commandNum++;
            }
        }
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            if(gp.ui.commandNum > 0){
                gp.ui.commandNum--;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
            }
            if(gp.ui.commandNum == 1){
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }

    public void titleState(int code){
        if(gp.ui.titleScreenState == 0) {

            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                if (gp.ui.commandNum < 2) {
                    gp.ui.commandNum++;
                }
            }
            if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                if (gp.ui.commandNum > 0) {
                    gp.ui.commandNum--;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum) {
                    case 0:
                        gp.ui.titleScreenState = 1;
                        //Character Collectino Music
//                            gp.playMusic(0);
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }
            }
        } else if(gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_RIGHT) {
                if (gp.ui.commandNum == 0) {
                    gp.ui.commandNum++;
                }
            }
            if (code == KeyEvent.VK_LEFT) {
                if (gp.ui.commandNum == 1) {
                    gp.ui.commandNum--;
                }
            }
            if(code == KeyEvent.VK_DOWN){
                gp.ui.commandNum = 2;
            }
            if(code == KeyEvent.VK_UP){
                gp.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum) {
                    case 0:
                        gp.gameState = gp.playState;
                        break;
                    case 1:
                        gp.gameState = gp.playState;

                        break;
                    case 2:
                        gp.ui.titleScreenState = 0;
                        break;
                }
            }}
    }

    public void playState(int code){
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_E){
            ePressed = true;
        }
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
        if(code == KeyEvent.VK_F){
            fPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.optionState;
        }
        if(code == KeyEvent.VK_M){
            gp.gameState = gp.mapState;
        }
        if(code == KeyEvent.VK_X){
            if(gp.map.miniMapOn){
                gp.map.miniMapOn = false;
            } else {
                gp.map.miniMapOn = true;
            }
        }

        //Debug
        if (code == KeyEvent.VK_T) {
            if (!checkDebugInfo) {
                checkDebugInfo = true;
            } else if (checkDebugInfo) {
                checkDebugInfo = false;
            }
        }
    }

    public void optionState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch(gp.ui.subState){
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
        }

        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            if(gp.ui.commandNum < maxCommandNum) {
                gp.ui.commandNum++;
            }
        }
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            if(gp.ui.commandNum > 0){
                gp.ui.commandNum--;
            }
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                }
            }
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0){
                    gp.se.volumeScale--;
                    gp.se.checkVolume();
                }
            }
        }
        if(code == KeyEvent.VK_D|| code == KeyEvent.VK_RIGHT){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                }
            }
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5){
                    gp.se.volumeScale++;
                    gp.se.checkVolume();
                }
            }
        }
    }

    public void dialogueState(int code){
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }

    public void pauseState(int code){
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code){
        if(code == KeyEvent.VK_C || code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if(gp.ui.slotRow >= 1){ gp.ui.slotRow --; }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if(gp.ui.slotRow <= 2) { gp.ui.slotRow ++; }
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if(gp.ui.slotCol >= 1) { gp.ui.slotCol--; }
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if(gp.ui.slotCol <= 3) { gp.ui.slotCol ++; }
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
    }

    public void noteState(int code){
        if(code == KeyEvent.VK_ENTER){
            gp.gameState = gp.characterState;
        }
    }

    public void mapState(int code){
        if(code == KeyEvent.VK_M || code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }
        if(code == KeyEvent.VK_E){
            ePressed = false;
        }
        if(code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
        if(code == KeyEvent.VK_F){
            fPressed = false;
        }
    }

}
