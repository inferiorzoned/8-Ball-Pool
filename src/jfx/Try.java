package jfx;


import java.util.ArrayList;

public class Try{
    public static ArrayList<Ball1> pottedBalls = new ArrayList<>();
    public static void setPottedBalls(){
        for(int i = 0; i<pottedBalls.size() ; i++ ){
            Ball1 b = pottedBalls.get(i);
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