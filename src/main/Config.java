package main;

import java.io.*;

public class Config {

    GamePanel gp;
    int loadCount;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            //full screen not done yet
            if(gp.fullScreenOn){
                bw.write("On");
            }
            if(!gp.fullScreenOn){
                bw.write("Off");
            }
            bw.newLine();

            //music volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            //se volume
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();

            bw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadConfig(){

        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
            String line = br.readLine();

            if(line.equals("On")){
                gp.fullScreenOn = true;
            }
            if(line.equals("Off")){
                gp.fullScreenOn = false;
            }

            //music
            line = br.readLine();
            gp.music.volumeScale = Integer.parseInt(line);

            //SE
            line = br.readLine();
            gp.se.volumeScale = Integer.parseInt(line);

            br.close();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

}
