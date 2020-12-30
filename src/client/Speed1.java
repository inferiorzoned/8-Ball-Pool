package client;
public class Speed1 {
    double velocity;
    double angle;

    public Speed1() {
        velocity = 0.0;
        angle = 0.0;
    }

    public Speed1(double velocity, double angle) {
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