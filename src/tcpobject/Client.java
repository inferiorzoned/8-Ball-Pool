package tcpobject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfx.Speed;
import jfx.Test;
import util.NetworkUtil;


public class Client extends Application {
	//	private static boolean myturn;
	NetworkUtil nc;
	Text fps = new Text(0, 30, "X");
	Player player;
	String playername;
	Text fps1 = new Text(400, 130, "SET SPEED");
	//ArrayList<Ball> currentlyPottedBalls = new ArrayList<>();
	long lastFrame = 0;
	long frameCount = 0;
	Speed queBallSpeed = new Speed();
	double queBallRadianAngle;
	static int counter = 1;
	//Ball ball;
	boolean start = false;
	boolean run = false;
	int i;
	static final int BALL_COUNT = 16;
	Ball[] balls = new Ball[BALL_COUNT];
	private Stick stick;
	Circle pivot = new Circle(0, 0, 8);
	Rectangle speedmeter;
	boolean setSpeed = true;
	Rectangle shootButton;
	Stage stage;

	public static void main(String args[]) {
		launch(args);
	}

	public void setPlayerName(String playername) {
		this.playername = playername;
	}

	public void startGame1() throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("player_name.fxml"));
		Parent root = loader.load();

		// Loading the controller
		Page_0_Controller controller = new Page_0_Controller();
		controller = loader.getController();
		controller.setMain(this);

		// Set the primary stage
		stage.setTitle("8 Ball Pool");
		stage.setScene(new Scene(root, 1000, 650));
		stage.show();
	}


	public void startGame2() throws Exception {
		try{
		nc.write(playername);
		//new ReadThread(nc,primaryStage);
		Object o = nc.read();
		Player p = (Player) o;
		System.out.println(p.name);

		//new Play(p, nc, primaryStage);
		//System.out.println("sdfasdfsdfsdfsfsdf");

		//starting play constructor
		this.player = p;
		//this.nc = nc;
		stage.setTitle("Testing 8 Ball Pool");
		Group root = new Group();
		Scene scene = new Scene(root, 850, 650);
		String path = Test.class.getResource("/racking_sound.mp3").toString();
		Media media = new Media(path);
		MediaPlayer mp = new MediaPlayer(media);
		mp.play();
		//ImageView table = new ImageView(new Image(getClass().getResourceAsStream("pool-board.png")),700,350,342,);
		//FXMLLoader loader = new FXMLLoader();
		//loader.setLocation(getClass().getResource("playing.fxml"));
		//root = loader.load();


		Image imgCover = new Image("frontpage.jpg");
		ImageView Coverpic = new ImageView();
		Coverpic.setImage(imgCover);
		Coverpic.setLayoutX(0);
		Coverpic.setLayoutY(0);
		Coverpic.setFitWidth(850);
		Coverpic.setFitHeight(650);
		Coverpic.setPreserveRatio(true);
		root.getChildren().add(Coverpic);

		Image img = new Image("pool-board-edited.jpg");
		ImageView poolTable = new ImageView();
		poolTable.setImage(img);
		poolTable.setLayoutX(50);
		poolTable.setLayoutY(150);
		poolTable.setFitHeight(400);
		poolTable.setFitWidth(750);
		poolTable.setPreserveRatio(true);
		root.getChildren().add(poolTable);

		/*Rectangle r2 = new Rectangle(0, 150, 700, 350);
		r2.setFill(Color.GREEN);
		root.getChildren().add(r2);//we will add an image later*/

		Rectangle borderOfSpeedmeter = new Rectangle(480, 80, 140, 55);
		borderOfSpeedmeter.setFill(Color.BLACK);
		root.getChildren().add(borderOfSpeedmeter);//we will add an image later


		/*Circle pot2 = new Circle(420.5, 170, 12);
		pot2.setFill(Color.RED);
		root.getChildren().add(pot2);//we will add an image later


		Circle pot5 = new Circle(419, 463, 12);
		pot5.setFill(Color.RED);
		root.getChildren().add(pot5);//we will add an image later*/


		shootButton = new Rectangle(650, 80, 30, 55);
		shootButton.setFill(Color.BLACK);
		root.getChildren().add(shootButton);//we will add an image later

		stage.setScene(scene);
		fps.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
		fps.setOpacity(0.4);
		root.getChildren().add(fps);
		fps1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		fps1.setOpacity(0.4);
		root.getChildren().add(fps1);

		stick = new Stick(root, 30, 150, 30, 250);
		speedmeter = new Rectangle(500, 100, 10, 15);
		speedmeter.setFill(Color.BLUE);
		root.getChildren().add(speedmeter);
		stick.isReadtoStrike = true;
		//setBalls(root);
		//QueBall = new Ball(root, "Queball",16,130,325,12.5,0);
		//Ball(Group root,String name,int ballNo, double x, double y, double radius, Paint fill, double velocity)

		setBalls(root);
		stage.show();
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				updateGameState(now);
			}

		}.start();

		//updateGameState();


		System.out.println("in play");

	} catch(
	Exception e)

	{
		System.out.println(e);
	}

}




	public void settingSpeed() {
		speedmeter.setOnMouseDragged(event -> {
			if (stick.isReadtoStrike == true ) {
				double mouseDeltaX = (event.getSceneX() - 510.0) * 1.0;
				if (mouseDeltaX > 0 && mouseDeltaX < 100) {
					speedmeter.setWidth(mouseDeltaX);
				} else if (mouseDeltaX >= 100.0) {
					speedmeter.setWidth(100.0);
				} else {
					speedmeter.setWidth(10.0);
				}

			}
			//mouseAngleReporter.setText(prefixMouse + Math.toDegrees(radAngle));
			//stickAngleReporter.setText(prefixstick + stickCurrentAngle());
		});
		//System.out.println("inside setSpeed");
		double speed = speedmeter.getWidth()/100.0;
		double angle = stickCurrentAngle();
		queBallSpeed.setVelocity(1000*speed);
		queBallSpeed.setAngle(angle+180);
	}

	public void settingtheQue(){
		stick.setOpacity(1.0);
		stick.setStartX(balls[15].getCenterX());
		stick.setStartY(balls[15].getCenterY());
		pivot.setTranslateX(stick.getStartX());
		pivot.setTranslateY(stick.getStartY());

		stick.setOnMouseDragged(event -> {
			double mouseDeltaX = event.getSceneX() - pivot.getTranslateX();
			double mouseDeltaY = event.getSceneY() - pivot.getTranslateY();
			double radAngle = Math.atan2(mouseDeltaY, mouseDeltaX);
			double[] res = rotatestick(pivot, radAngle - Math.toRadians(stickCurrentAngle()), stick.getEndX(), stick.getEndY());

			stick.setEndX(res[0]);
			stick.setEndY(res[1]);
			//mouseAngleReporter.setText(prefixMouse + Math.toDegrees(radAngle));
			//stickAngleReporter.setText(prefixstick + stickCurrentAngle());
		});

	}

	private void updateGameState(long now) {
		double elapsedSec;
		elapsedSec = (now - lastFrame) / 1000000000.0;
		System.out.println("elapsed = " + elapsedSec);
		if (frameCount % 10 == 0)
            fps.setText(String.format("%.1f", 1/elapsedSec));
        lastFrame = now;
		//here the Que is being set to strike
		//stick.isReadtoStrike = true;//during the game it will be kept true/false according to the situation
		//System.out.println(player.isMyturn());
			if (player.isMyturn()) {
				if (stillBalls() == true) {
					stick.isReadtoStrike = true;
					setSpeed = true;
				}
				if (stick.isReadtoStrike == true) {
					//System.out.println("in stick's state " + stick.isReadtoStrike);
					settingtheQue();
				} else {
					stick.setOpacity(0.0);
				}

				balls[15].iv.setOnMouseDragged(e -> {
					if (balls[15].isPotted == true) {
						balls[15].setCenterX(e.getSceneX());
						balls[15].setCenterY(e.getSceneY());
					}
				});
				// it will be set false while after rotating the que is set for shooting
				//or when the player sets the speed meter
				if (setSpeed == true) {
					settingSpeed();
					queBallRadianAngle = (queBallSpeed.getAngle() * Math.PI) / 180;
					balls[15].vx = queBallSpeed.getVelocity() * Math.cos(queBallRadianAngle);
					balls[15].vy = queBallSpeed.getVelocity() * Math.sin(queBallRadianAngle);
				}

				shootButton.setOnMouseClicked(event -> {
					if (setSpeed == true && stick.isReadtoStrike == true) {
						String path = Test.class.getResource("/ball_shot.mp3").toString();
						Media media = new Media(path);
						MediaPlayer mp = new MediaPlayer(media);
						mp.play();
						stick.isReadtoStrike = false;
						setSpeed = false;
						balls[15].isPotted = false;
						queBallSpeed.setBallX(balls[15].getCenterX());
						queBallSpeed.setBallY(balls[15].getCenterY());
						System.out.println("que  "+ queBallSpeed.getVelocity() +"  " + queBallSpeed.getAngle());
						nc.refresh();
						nc.write(queBallSpeed);
						System.out.println("wrote the speed");
                    /*System.out.println("the state of " + stick.isReadtoStrike + "  " + setSpeed);
                    System.out.println("in cueBall's state " + balls[15].isPotted);*/
						counter++;
						if (counter > 1) {
							nc.refresh();
							nc.write(new Speed(0, 0));
							player.setMyturn(false);
						}
					}
				});
				//System.out.println("in cueBall's state " + balls[15].isPotted);
        /*if(counter%2 == 0){
            if(player.isMyturn() == true){
                System.out.println(counter);
                player.setMyturn(false);
            }
            else {
                System.out.println(counter);
                player.setMyturn(true);
            }
        }*/
				if ((frameCount > 0) && (stick.isReadtoStrike == false) && (setSpeed == false)) {
					for (Ball b : balls) {
						b.detectWallCollisions();
						b.updatePosition(0.01609);
						b.potCheck();
						for (i = 0; i < BALL_COUNT; i++) {
							if (balls[i] == b) {//do nothing
							} else {
								b.ballCollisions(balls[i]);
							}
						}
						//balls[15].isPotted = false;
						b.retardation();
					}
					//QueBall.detectWallCollisions();
					//QueBall.updatePosition(elapsedSec);
					//retardation(QueBall);
					String temp = Long.toString(frameCount);
				}
			} else {
				stick.setOpacity(0.0);
				setSpeed = false;
				stick.isReadtoStrike = false;
				// it will be set false while after rotating the que is set for shooting
				//or when the player sets the speed meter
				System.out.println(player.isMyturn());
				if(stillBalls()) {
					Object o = nc.read();
					System.out.println("o " + o);
					if (o instanceof Speed) {
						System.out.println("read a speed");
						Speed s = (Speed) o;
						System.out.println(s.getVelocity() + " " + s.getAngle());
						System.out.println("ballx = "+ s.getBallX()+ ", " + "bally = " + s.getBallY());
						queBallSpeed = s;
						queBallRadianAngle = (queBallSpeed.getAngle() * Math.PI) / 180;
						//balls[15].setCenterX(s.getBallX());
						//balls[15].setCenterY(s.getBallY());
						balls[15].vx = queBallSpeed.getVelocity() * Math.cos(queBallRadianAngle);
						balls[15].vy = queBallSpeed.getVelocity() * Math.sin(queBallRadianAngle);

						if (queBallSpeed.getVelocity() == 0 && queBallSpeed.getAngle() == 0) {
							player.setMyturn(true);
						}

					}
				}
				if ((frameCount >= 0) && (stick.isReadtoStrike == false) && (setSpeed == false)) {
					for (Ball b : balls) {
						//System.out.println("inside update");
						b.detectWallCollisions();
						b.updatePosition(0.01609);
						b.potCheck();
						for (i = 0; i < BALL_COUNT; i++) {
							if (balls[i] == b) {//do nothing
							} else {
								b.ballCollisions(balls[i]);
							}
						}
						//balls[15].isPotted = false;
						b.retardation();
					}
					//QueBall.detectWallCollisions();
					//QueBall.updatePosition(elapsedSec);
					//retardation(QueBall);
					String temp = Long.toString(frameCount);
				}
				//System.out.println("in cueBall's state " + balls[15].isPotted);
        /*if(counter%2 == 0){
            if(player.isMyturn() == true){
                System.out.println(counter);
                player.setMyturn(false);
            }
            else {
                System.out.println(counter);
                player.setMyturn(true);
            }
        }*/
			}

			fps1.setText("SET SPEED");
			frameCount++;
	}

	private boolean stillBalls() {
		boolean check = true;
		for (Ball b : balls) {
			if(b.vx != 0 || b.vy != 0){
				check = false;
				return check;
			}
		}
		return check;
	}

	private double stickCurrentAngle(){
		double angle = Math.toDegrees(Math.atan2(stick.getEndY() - pivot.getTranslateY(), stick.getEndX() - pivot.getTranslateX()));
		//System.out.println("the angle is : "+angle);
		return  angle;
	}

	private double[] rotatestick(Shape pivot, double radAngle, double endX, double endY) {
		double x, y;
		x = Math.cos(radAngle) * (endX - pivot.getTranslateX()) - Math.sin(radAngle) * (endY - pivot.getTranslateY()) + pivot.getTranslateX();
		y = Math.sin(radAngle) * (endX - pivot.getTranslateX()) + Math.cos(radAngle) * (endY - pivot.getTranslateY()) + pivot.getTranslateY();
		return new double[]{x, y};
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			//Scanner input = new Scanner(System.in);
			String serverAddress = "localhost";
			int serverPort = 44444;
			this.nc = new NetworkUtil(serverAddress,serverPort);
			stage = primaryStage;
			showFirstPage();
			/**System.out.println("enter your name : ");
			String s = input.nextLine();
			nc.write(s);
			//new ReadThread(nc,primaryStage);
			Object o = nc.read();
			Player  p = (Player) o;
			System.out.println(p.name);
			/**Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						new Play(p,nc,primaryStage);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			//new Play(p, nc, primaryStage);
			//System.out.println("sdfasdfsdfsdfsfsdf");



			//starting play constructor
			this.player = p;
			//this.nc = nc;
			primaryStage.setTitle("Testing 8 Ball Pool");
			Group root = new Group();
			Scene scene = new Scene(root, 850, 650);
			String path = Test.class.getResource("/racking_sound.mp3").toString();
			Media media = new Media(path);
			MediaPlayer mp = new MediaPlayer(media);
			mp.play();
			//ImageView table = new ImageView(new Image(getClass().getResourceAsStream("pool-board.png")),700,350,342,);
			//FXMLLoader loader = new FXMLLoader();
			//loader.setLocation(getClass().getResource("playing.fxml"));
			//root = loader.load();


			Image imgCover = new Image("frontpage.jpg");
			ImageView Coverpic = new ImageView();
			Coverpic.setImage(imgCover);
			Coverpic.setLayoutX(0);
			Coverpic.setLayoutY(0);
			Coverpic.setFitWidth(850);
			Coverpic.setFitHeight(650);
			Coverpic.setPreserveRatio(true);
			root.getChildren().add(Coverpic);

			Image img = new Image("pool-board-edited.jpg");
			ImageView poolTable = new ImageView();
			poolTable.setImage(img);
			poolTable.setLayoutX(50);
			poolTable.setLayoutY(150);
			poolTable.setFitHeight(400);
			poolTable.setFitWidth(750);
			poolTable.setPreserveRatio(true);
			root.getChildren().add(poolTable);

        Rectangle r2 = new Rectangle(0, 150, 700, 350);
        r2.setFill(Color.GREEN);
        root.getChildren().add(r2);//we will add an image later

			Rectangle borderOfSpeedmeter = new Rectangle(480, 80, 140, 55);
			borderOfSpeedmeter.setFill(Color.BLACK);
			root.getChildren().add(borderOfSpeedmeter);//we will add an image later


			Circle pot2 = new Circle(420.5, 170, 12);
			pot2.setFill(Color.RED);
			root.getChildren().add(pot2);//we will add an image later


			Circle pot5 = new Circle(419, 463, 12);
			pot5.setFill(Color.RED);
			root.getChildren().add(pot5);//we will add an image later



			shootButton = new Rectangle(650, 80, 30, 55);
			shootButton.setFill(Color.BLACK);
			root.getChildren().add(shootButton);//we will add an image later

			primaryStage.setScene(scene);
			fps.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
			fps.setOpacity(0.4);
			root.getChildren().add(fps);
			fps1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
			fps1.setOpacity(0.4);
			root.getChildren().add(fps1);

			stick = new Stick(root,30,150,30,250);
			speedmeter = new Rectangle(500,100,10,15);
			speedmeter.setFill(Color.BLUE);
			root.getChildren().add(speedmeter);
			stick.isReadtoStrike = true;
			//setBalls(root);
			//QueBall = new Ball(root, "Queball",16,130,325,12.5,0);
			//Ball(Group root,String name,int ballNo, double x, double y, double radius, Paint fill, double velocity)

			setBalls(root);
			primaryStage.show();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGameState(now);
            }

        }.start();

			//updateGameState();


			System.out.println("in play");
**/
		} catch(Exception e) {
			System.out.println (e);
		}
	}

	public void showFirstPage() throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Page_0_fxml.fxml"));
		Parent root = loader.load();

		// Loading the controller
		Page_0_Controller controller = loader.getController();
		controller.setMain(this);

		// Set the primary stage
		stage.setTitle("8 Ball Pool");
		stage.setScene(new Scene(root, 1000, 650));
		stage.show();
	}


	private void setBalls(Group root) {
		balls[0] = new Ball(root,"Solid",1, 550 , 325, 10, 0);
		balls[1] = new Ball(root,"Solid",2, 570 , 315, 10, 0);
		balls[2] = new Ball(root,"Solid",3, 570 , 335, 10, 0);
		balls[3] = new Ball(root,"Solid",4, 590 , 325, 10, 0);
		balls[4] = new Ball(root,"Solid",5, 590 , 305, 10, 0);
		balls[5] = new Ball(root,"Solid",6, 590 , 345, 10, 0);
		balls[6] = new Ball(root,"Solid",7, 610 , 295, 10, 0);
		balls[7] = new Ball(root,"Stripes",8, 610 , 315, 10, 0);
		balls[8] = new Ball(root,"Stripes",9, 610 , 335, 10, 0);
		balls[9] = new Ball(root,"Stripes",10, 610 , 355, 10, 0);
		balls[10] = new Ball(root,"Stripes",11, 630 , 325, 10, 0);
		balls[11] = new Ball(root,"Stripes",12, 630 , 305, 10, 0);
		balls[12] = new Ball(root,"Stripes",13, 630 , 285, 10, 0);
		balls[13] = new Ball(root,"Stripes",14, 630 , 345, 10, 0);
		balls[14] = new Ball(root,"Stripes",15, 630 , 365, 10, 0);
		balls[15] = new Ball(root, "Queball",16,250,325,10,0);

	}
}
