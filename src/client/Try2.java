package client;

import java.util.ArrayList;

public class Try2 {
    public static ArrayList<Ball> pottedBalls = new ArrayList<>();
    public static void setPottedBalls(){
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