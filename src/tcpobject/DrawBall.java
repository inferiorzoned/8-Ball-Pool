package tcpobject;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DrawBall {
    String name;
    Image img;
    ImageView iv;
    int ballNo;
    double vx;
    double vy;
    double centerX;
    double centerY;
    double radius;
    double angle;
    double acc;
    double time;
    boolean isPotted;

    public DrawBall(Group root, String s, int ballNo, double x, double y, double radius) {
        /** double vx=0;
         double vy=0;
         double angle=0;
         double time=0;
         double a=7;**/

        if(s.equals("Queball")){
            this.img = new Image("cueball.png");
        }
        else{
            this.img = new Image("Ball" + ballNo + ".png");
        }
        this.iv = new ImageView();
        this.ballNo = ballNo;
        this.radius = radius;
        this.centerX = x;
        this.centerY = y;
        iv.setImage(img);
        iv.setPreserveRatio(true);
        iv.setFitWidth(25);
        iv.setLayoutX(x-12.5);
        iv.setLayoutY(y-12.5);
        //super(x, y, fill, radius);
        this.name = name;

        this.vx = 0;//Math.sin(angle) * velocity;
        this.vy = 0;//Math.cos(angle) * velocity;
        root.getChildren().add(iv);

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBallNo() {
        return ballNo;
    }

    public void setBallNo(int ballNo) {
        this.ballNo = ballNo;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAcc() {
        return acc;
    }

    public void setAcc(double acc) {
        this.acc = acc;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public boolean isPotted() {
        return isPotted;
    }

    public void setPotted(boolean potted) {
        isPotted = potted;
    }

}
