package jfx;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Ball1 {
	public String getName() {
		return name;
	}

	String name;
	Image img;
	public ImageView iv;
	int ballNo;
	public double vx;
	public double vy;
	double centerX;
	double centerY;
	double radius;
	public boolean isPotted;


	public Ball1(Group root, String name, int ballNo, double x, double y, double radius, double velocity) {
		if(name.equals("Queball")){
			this.img = new Image("cueball.png");
		}
		else{
			this.img = new Image("BAll" + ballNo + ".png");
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
		//root.getChildren().add(this);
	}

	public void updatePosition(double elapsed) {
		setCenterX(getCenterX() + (vx * elapsed));
		setCenterY(getCenterY() + (vy * elapsed));
		//iv.setLayoutX(getCenterX() + (vx * elapsed));
		//iv.setLayoutY(getCenterY() + (vx * elapsed));
	}
	public void ballCollisions(Ball1 b){
		double deltaX = b.getCenterX() - this.getCenterX() ;
		double deltaY = b.getCenterY() - this.getCenterY() ;
		if (colliding(this, b, deltaX, deltaY)) {
			bounce(this, b, deltaX, deltaY);
			//System.out.println("in colliding state");
		}

	}

	public void bounce(Ball1 b1, Ball1 b2, double deltaX, double deltaY){
		double distance = sqrt(deltaX * deltaX + deltaY * deltaY) ;
		double unitContactX = deltaX / distance ;
		double unitContactY = deltaY / distance ;

		double xVelocity1 = b1.vx;
		double yVelocity1 = b1.vy;
		double xVelocity2 = b2.vx;
		double yVelocity2 = b2.vy;

		double u1 = xVelocity1 * unitContactX + yVelocity1 * unitContactY ; // velocity of ball 1 parallel to contact vector
		double u2 = xVelocity2 * unitContactX + yVelocity2 * unitContactY ; // same for ball 2

        /*final double massSum = b1.getMass() + b2.getMass() ;
        final double massDiff = b1.getMass() - b2.getMass() ;

        final double v1 = ( 2*b2.getMass()*u2 + u1 * massDiff ) / massSum ; // These equations are derived for one-dimensional collision by
        final double v2 = ( 2*b1.getMass()*u1 - u2 * massDiff ) / massSum ; // solving equations for conservation of momentum and conservation of energy*/

		double v1 = u2;
		double v2 = u1;

		double u1PerpX = xVelocity1 - u1 * unitContactX ; // Components of ball 1 velocity in direction perpendicular
		double u1PerpY = yVelocity1 - u1 * unitContactY ; // to contact vector. This doesn't change with collision
		double u2PerpX = xVelocity2 - u2 * unitContactX ; // Same for ball 2....
		double u2PerpY = yVelocity2 - u2 * unitContactY ;

		b1.vx = (v1 * unitContactX + u1PerpX);
		b1.vy = (v1 * unitContactY + u1PerpY);
		b2.vx = (v2 * unitContactX + u2PerpX);
		b2.vy = (v2 * unitContactY + u2PerpY);
	}

	private boolean colliding(Ball1 b1, Ball1 b2, double deltaX, double deltaY) {
		double radiusSum = b1.getRadius() + b2.getRadius();
		if (deltaX * deltaX + deltaY * deltaY <= radiusSum * radiusSum) {
			if ( deltaX * (b2.vx - b1.vx)
					+ deltaY * (b2.vy - b1.vy) < 0) {
				return true;
			}
		}
		return false;
	}

	public void detectWallCollisions() {
		if (getCenterX() < getRadius()) {
			setCenterX(getRadius());
			vx = -vx;
		} else if (getCenterX() > (700 - getRadius())) {
			setCenterX(700 - getRadius());
			vx = -vx;
		}
		if (getCenterY() > (500 - getRadius())) {//getScene().getHeight()
			setCenterY(500 - getRadius());
			vy = -vy;
		}else if(getCenterY() >50 && getCenterY() < (150 + getRadius())){
			setCenterY(150 + getRadius());
			vy = -vy;
		}

	}


	public void potCheck(){
		double deltaX1 = this.getCenterX()- 15;
		double deltaY1 = this.getCenterY()- 165;
		double deltaX2 = this.getCenterX()- 350;
		double deltaY2 = this.getCenterY()- 165;
		double deltaX3 = this.getCenterX()- 685;
		double deltaY3 = this.getCenterY()- 165;
		double deltaX4 = this.getCenterX()- 15;
		double deltaY4 = this.getCenterY()- 485;
		double deltaX5 = this.getCenterX()- 350;
		double deltaY5 = this.getCenterY()- 485;
		double deltaX6 = this.getCenterX()- 685;
		double deltaY6 = this.getCenterY()- 485;
		double dis1 = Math.sqrt((deltaX1*deltaX1)+(deltaY1*deltaY1));
		double dis2 = Math.sqrt((deltaX2*deltaX2)+(deltaY2*deltaY2));
		double dis3 = Math.sqrt((deltaX3*deltaX3)+(deltaY3*deltaY3));
		double dis4 = Math.sqrt((deltaX4*deltaX4)+(deltaY4*deltaY4));
		double dis5 = Math.sqrt((deltaX5*deltaX5)+(deltaY5*deltaY5));
		double dis6 = Math.sqrt((deltaX6*deltaX6)+(deltaY6*deltaY6));
		if(dis1 <= 9 || dis2 <= 9 || dis3 <= 9 || dis4 <= 9 || dis5 <= 9 || dis6 <= 9){
			this.setVx(0);
			this.setVy(0);
			this.isPotted = true;
			Try.pottedBalls.add(this);
			Try.setPottedBalls();
		}
	}

	public void retardation(){
		if(this.vx >= 1.0){
			this.vx--;
		}
		else if(this.vx >= 0 && (this.vx-1)<0){
			this.vx = 0;
		}
		else if(this.vx <0 && (this.vx+1)>0){
			this.vx = 0;
		}
		else {
			this.vx++;
		}
		if(this.vy >= 1.0){
			this.vy--;
		}
		else if(this.vy >= 0 && (this.vy-1)<0){
			this.vy = 0;
		}
		else if(this.vy <0 && (this.vy+1)>0){
			this.vy = 0;
		}
		else {
			this.vy++;
		}
	}


	public void setCenterX(double i){
		iv.setLayoutX(i-12.5);
		this.centerX = i;
	}

	public void setCenterY(double i){
		iv.setLayoutY(i-12.5);
		this.centerY = i;
	}

	public double getCenterX(){
		return this.centerX;
	}

	public double getCenterY(){
		return this.centerY;
	}

	public double getRadius(){
		return this.radius;
	}

	private void setVy(double i) {
		this.vy = i;
	}

	private void setVx(double i) {
		this.vx = i;
	}
}
