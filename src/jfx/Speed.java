package jfx;

import java.io.Serializable;

public class Speed implements Serializable{
    double velocity;
    double angle;
    String name;
    double ballX;
    double ballY;

    public void setBallX(double ballX) {
        this.ballX = ballX;
    }

    public void setBallY(double ballY) {
        this.ballY = ballY;
    }

    public double getBallX() {

        return ballX;
    }

    public double getBallY() {
        return ballY;
    }

    public String getName() {
        return name;
    }

    public Speed(double velocity, double angle, String name) {

        this.velocity = velocity;
        this.angle = angle;
        this.name = name;
    }

    public Speed() {
        velocity = 0.0;
        angle = 0.0;
    }

    public Speed(double velocity, double angle) {
        this.velocity = velocity;
        this.angle = angle;
    }

    public void setVelocity(double velocity) {

        this.velocity = velocity;
    }

    public void setAngle(double angle) {

        this.angle = angle;
    }

    public double getVelocity() {

        return velocity;
    }

    public double getAngle() {
        return angle;
    }
}