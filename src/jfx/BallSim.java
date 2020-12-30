package jfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tcpobject.Ball;

public class BallSim extends Application {
	Text fps = new Text(0, 30, "X");
	Text fps1 = new Text(0, 300, "HI");
	long lastFrame = 0;
	long frameCount = 0;
	//Ball ball;
	ImageView iv3;
	int i;
	static final int BALL_COUNT = 6;
	Ball[] balls = new Ball[BALL_COUNT];
	
	private void updateGameState(long now) {
		double elapsedSec = (now - lastFrame) / 10000000000.0;
		if (frameCount % 10 == 0)
			fps.setText(String.format("%.1f", 1/elapsedSec));
		lastFrame = now;
		if (frameCount > 0) {

			iv3.setLayoutX(iv3.getLayoutX()+5);
			iv3.setLayoutY(iv3.getLayoutY()+5);
			System.out.println("iv3 er x :"+ iv3.getLayoutX());
			System.out.println("iv3 er y :"+ iv3.getLayoutY());
			/*for (Ball b : balls) {
				b.detectWallCollisions();
				b.updatePosition(elapsedSec);
				for(i = 0; i < BALL_COUNT ; i++){
					if(balls[i] == b){//do nothing
					}
					else{
						//b.detectBallCollisions(balls[i]);
					}
				}
			}*/
			String temp = Long.toString(frameCount);
			fps1.setText(temp);
		}
		frameCount++;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Ball Sim");
		Group root = new Group();
		Scene scene = new Scene(root, 800, 700);
		primaryStage.setScene(scene);
		fps.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		fps.setOpacity(0.4);
		root.getChildren().add(fps);
		fps1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		fps1.setOpacity(0.4);
		root.getChildren().add(fps1);

		javafx.scene.image.Image image = new Image("Ball1.png");
		iv3 = new ImageView();
		iv3.setImage(image);
		//Rectangle2D viewportRect = new Rectangle2D(400, 300, image.getWidth(), image.getHeight());
		//iv3.setViewport(viewportRect);
		iv3.setLayoutX(300);
		iv3.setLayoutY(200);
		System.out.println("the x is :"+iv3.getX());
		root.getChildren().add(iv3);

		//ball = new Ball(root, 150, 150, 5, Color.RED, 250, 250);
		//ball = new Ball(root, 150, 150, 5, Color.RED, 250);
		//for (int i = 0; i < BALL_COUNT; i++) {
			//balls[i] =  new Ball(root, 150, 150, 5, Color.RED, 250);
		//}
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				updateGameState(now);
			}
			
		}.start();
		
		
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
