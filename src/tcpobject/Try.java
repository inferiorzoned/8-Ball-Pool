package tcpobject;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import jfx.Test;
import tcpobject.Ball;

import java.util.ArrayList;

public class Try{
    public static ArrayList<Ball> currentlyPottedBalls = new ArrayList<>();
    public static ArrayList<Ball> pottedBalls = new ArrayList<>();
    public static void setPottedBalls(){
        String path = Test.class.getResource("/ball_potting.mp3").toString();
        Media media = new Media(path);
        MediaPlayer mp = new MediaPlayer(media);
        mp.play();
        for(int i = 0; i<pottedBalls.size() ; i++ ){
            Ball b = pottedBalls.get(i);
            if(b.getName().equals("Queball")){
                b.setCenterX(130);
                b.setCenterY(325);
                pottedBalls.remove(b);
            }
            else {
                b.setCenterX(485 - i * 30);
                b.setCenterY(50);
            }
        }
    }
}