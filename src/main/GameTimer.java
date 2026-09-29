package main;

public class GameTimer {

    GamePanel gp;
    int timer;
    int interval;
    public boolean running;
    public boolean timeUp;

    public GameTimer(GamePanel gp) {
        this.gp = gp;
        this.timer = 0;
        this.interval = 5*60;
        this.running = false;
        this.timeUp = false;
    }

    public void update(){
        if(!running){
            return;
        }
        else {
            timer++;
            System.out.println(timer);

            if (timer >= interval) {
                timer = 0;
                timeUp = true;
                running = false;
                System.out.println(timeUp);
            }
        }
    }

    public void start(){
        timeUp = false;
        running = true;
        timer = 0;
    }
}
