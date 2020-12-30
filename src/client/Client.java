package client;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfx.Speed;
import jfx.Stick;
import sample.NetworkUtil;

import java.util.Scanner;

public class Client extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        new ClientClass(primaryStage);

    }
}

class ClientClass {
    Stage primaryStage;
    NetworkUtil networkUtil;
    boolean myMove;
    Object receiveData;
    Thread thread;
    String ipAdress = "localhost";
    int port = 55555;
    Text fps = new Text(0, 30, "X");
    Text fps1 = new Text(400, 130, "SET SPEED");
    long lastFrame = 0;
    long frameCount = 0;
    Speed queBallSpeed = new Speed();
    double queBallRadianAngle;
    int counter = 0;
    //Ball ball;
    boolean start = false;
    boolean run = false;
    int i;
    static final int BALL_COUNT = 16;
    Ball[] balls = new Ball[BALL_COUNT];
    public Stick stick;
    Circle pivot = new Circle(0, 0, 8);
    Rectangle speedmeter;
    boolean setSpeed = true;
    Rectangle shootButton;

    public void settingSpeed() {
        speedmeter.setOnMouseDragged(event -> {
            double mouseDeltaX = (event.getSceneX() - 510.0) * 1.0;
            if (mouseDeltaX > 0 && mouseDeltaX < 100) {
                speedmeter.setWidth(mouseDeltaX);
            } else if (mouseDeltaX >= 100.0) {
                speedmeter.setWidth(100.0);
            } else {
                speedmeter.setWidth(10.0);
            }

            //mouseAngleReporter.setText(prefixMouse + Math.toDegrees(radAngle));
            //stickAngleReporter.setText(prefixstick + stickCurrentAngle());
        });
        System.out.println("inside setSpeed");
        double speed = speedmeter.getWidth() / 100.0;
        double angle = stickCurrentAngle();
        queBallSpeed.setVelocity(1000 * speed);
        queBallSpeed.setAngle(angle + 180);

    }

    public void settingtheQue() {
        stick.setOpacity(1.0);
        stick.setStartX(balls[15].getCenterX());
        stick.setStartY(balls[15].getCenterY());
        pivot.setTranslateX(stick.getStartX());
        pivot.setTranslateY(stick.getStartY());

        stick.setOnMouseDragged(event -> {
            double mouseDeltaX = event.getSceneX() - pivot.getTranslateX();
            double mouseDeltaY = event.getSceneY() - pivot.getTranslateY();
            System.out.println(event.getSceneX() + " " + event.getSceneY());
            double radAngle = Math.atan2(mouseDeltaY, mouseDeltaX);
            double[] res = rotatestick(pivot, radAngle - Math.toRadians(stickCurrentAngle()), stick.getEndX(), stick.getEndY());

            stick.setEndX(res[0]);
            stick.setEndY(res[1]);

            //mouseAngleReporter.setText(prefixMouse + Math.toDegrees(radAngle));
            //stickAngleReporter.setText(prefixstick + stickCurrentAngle());
        });

    }

    private void updateGameState(long now) {
        double elapsedSec = (now - lastFrame) / 1000000000.0;
        if (frameCount % 10 == 0)
            fps.setText(String.format("%.1f", 1 / elapsedSec));
        lastFrame = now;
        //here the Que is being set to strike
        //stick.isReadtoStrike = true;//during the game it will be kept true/false according to the situation
        counter++;
        System.out.println(counter);
        if (stillBalls()) {
            stick.isReadtoStrike = true;
            setSpeed = true;
        }
        if (stick.isReadtoStrike) {
            stick.setOpacity(1.0);
            System.out.println("in stick's state " + stick.isReadtoStrike);
            settingtheQue();
        } else {
            stick.setOpacity(0.0);
        }
        balls[15].iv.setOnDragDetected(event -> {
            if (balls[15].isPotted) {
                balls[15].iv.setOnMouseDragged(e -> {
                    balls[15].setCenterX(e.getSceneX());
                    balls[15].setCenterY(e.getSceneY());
                });
            }
            balls[15].isPotted = false;
        });
/*        if(balls[15].isPotted){
            balls[15].iv.setOnMouseDragged(event -> {
                balls[15].setCenterX(event.getSceneX());
                balls[15].setCenterY(event.getSceneY());
            });
        }
*/        // it will be set false while after rotating the que is set for shooting
        //or when the player sets the speed meter
        if (setSpeed) {
            settingSpeed();
            queBallRadianAngle = (queBallSpeed.getAngle() * Math.PI) / 180;
            balls[15].vx = queBallSpeed.getVelocity() * Math.cos(queBallRadianAngle);
            balls[15].vy = queBallSpeed.getVelocity() * Math.sin(queBallRadianAngle);
        }

        if (setSpeed && stick.isReadtoStrike) {

            shootButton.setOnMouseClicked(event -> {
                stick.isReadtoStrike = false;
                setSpeed = false;
                balls[15].isPotted = false;
                System.out.println("the state of " + stick.isReadtoStrike + "  " + setSpeed);
                System.out.println("in queBall's state " + balls[15].isPotted);
            });
        }
        System.out.println("in queBall's state " + balls[15].isPotted);
        if (frameCount > 0 && !stick.isReadtoStrike && !setSpeed) {
            for (Ball b : balls) {
                b.detectWallCollisions();
                b.updatePosition(elapsedSec);
                b.potCheck();
                System.out.println("in stick's state " + stick.isReadtoStrike);
                System.out.println("in queBall's state " + balls[15].isPotted);
                System.out.println("the x and y of the ball image is :" + b.iv.getLayoutX() + "   " + b.iv.getLayoutY());
                System.out.println("the x and y of the ball center is :" + b.getCenterX() + "   " + b.getCenterY());
                System.out.println("the radius of the ball image is :" + b.iv.getFitWidth());
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

        fps1.setText("SET SPEED");
        frameCount++;
    }

    private boolean stillBalls() {
        boolean check = true;
        for (Ball b : balls) {
            if (b.vx != 0 || b.vy != 0) {
                check = false;
                return check;
            }
        }
        return check;
    }


    private double stickCurrentAngle() {
        double angle = Math.toDegrees(Math.atan2(stick.getEndY() - pivot.getTranslateY(), stick.getEndX() - pivot.getTranslateX()));
        System.out.println("the angle is : " + angle);
        return angle;
    }

    private double[] rotatestick(Shape pivot, double radAngle, double endX, double endY) {
        double x, y;
        x = Math.cos(radAngle) * (endX - pivot.getTranslateX()) - Math.sin(radAngle) * (endY - pivot.getTranslateY()) + pivot.getTranslateX();
        y = Math.sin(radAngle) * (endX - pivot.getTranslateX()) + Math.cos(radAngle) * (endY - pivot.getTranslateY()) + pivot.getTranslateY();
        return new double[]{x, y};
    }



    private void setBalls(Group root) {
        balls[0] = new Ball(root, "Solid", 1, 450, 325, 12.5, 0);
        balls[1] = new Ball(root, "Solid", 2, 475, 312.5, 12.5, 0);
        balls[2] = new Ball(root, "Solid", 3, 475, 337.5, 12.5, 0);
        balls[3] = new Ball(root, "Solid", 4, 500, 325, 12.5, 0);
        balls[4] = new Ball(root, "Solid", 5, 500, 300, 12.5, 0);
        balls[5] = new Ball(root, "Solid", 6, 500, 350, 12.5, 0);
        balls[6] = new Ball(root, "Solid", 7, 525, 287.5, 12.5, 0);
        balls[7] = new Ball(root, "Stripes", 8, 525, 312.5, 12.5, 0);
        balls[8] = new Ball(root, "Stripes", 9, 525, 337.5, 12.5, 0);
        balls[9] = new Ball(root, "Stripes", 10, 525, 362.5, 12.5, 0);
        balls[10] = new Ball(root, "Stripes", 11, 550, 325, 12.5, 0);
        balls[11] = new Ball(root, "Stripes", 12, 550, 275, 12.5, 0);
        balls[12] = new Ball(root, "Stripes", 13, 550, 300, 12.5, 0);
        balls[13] = new Ball(root, "Stripes", 14, 550, 350, 12.5, 0);
        balls[14] = new Ball(root, "Stripes", 15, 550, 375, 12.5, 0);
        balls[15] = new Ball(root, "Queball", 16, 130, 325, 12.5, 0);

    }



        public ClientClass(Stage primaryStage) {
            changeIpAndPort();
            networkUtil = new NetworkUtil(ipAdress, port);
            if (networkUtil.isAvaiable()) {
                System.out.println("inside netutil");
                //String str = (String) networkUtil.read();
                //System.out.println(str);
                /*if (str != null) {
                    String string[] = str.split("\\+");
                    if (string[0].equalsIgnoreCase("start")) {
                        if (string[1].equalsIgnoreCase("firstPlayer")) {
                            myMove = true;
                        } else {
                            myMove = false;
                        }
                    }

                }*/
                startGame(primaryStage);
            } else {
            /*  Write your own code here
             *   Here in GUI show that server not connected
             */
            }
            try {
                if (thread != null)
                    thread.join();
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }

        }


        public void changeIpAndPort() {
        /*
         * Write your own ip and port here
         */
            ipAdress = "localhost";
            port = 55555;
        }

        public void startGame(Stage primaryStage) {
            System.out.println("game has started");
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (myMove) {
                            Speed1 s = move();
                            networkUtil.write(s);
                            starttheanimation(s ,primaryStage);
                            myMove = false;
                        } else {
                            receiveData = networkUtil.read();
                            System.out.println(receiveData);
                            if (receiveData == null || ((String) receiveData).equals("EXIT")) {
                                return;
                            }

                            performMove(receiveData);
                            myMove = true;
                        }
                    }
                }

            };
            thread = new Thread(runnable);
            thread.start();
        }

        private void starttheanimation(Speed1 s,Stage primaryStage) {
            primaryStage.setTitle("Testing 8 Ball Pool");
            Group root = new Group();
            Scene scene = new Scene(root, 700, 550);
            //ImageView table = new ImageView(new Image(getClass().getResourceAsStream("pool-board.png")),700,350,342,);
            //FXMLLoader loader = new FXMLLoader();
            //loader.setLocation(getClass().getResource("playing.fxml"));
            //root = loader.load();


            Rectangle r1 = new Rectangle(0, 0, 700, 150);
            r1.setFill(Color.BROWN);
            root.getChildren().add(r1);//we will add an image later


            Rectangle r2 = new Rectangle(0, 150, 700, 350);
            r2.setFill(Color.GREEN);
            root.getChildren().add(r2);//we will add an image later

            Rectangle borderOfSpeedmeter = new Rectangle(480, 80, 140, 55);
            borderOfSpeedmeter.setFill(Color.BLACK);
            root.getChildren().add(borderOfSpeedmeter);//we will add an image later

            Circle pot1 = new Circle(15, 165, 15);
            pot1.setFill(Color.BLACK);
            root.getChildren().add(pot1);//we will add an image later

            Circle pot2 = new Circle(350, 165, 15);
            pot2.setFill(Color.BLACK);
            root.getChildren().add(pot2);//we will add an image later

            Circle pot3 = new Circle(685, 165, 15);
            pot3.setFill(Color.BLACK);
            root.getChildren().add(pot3);//we will add an image later

            Circle pot4 = new Circle(15, 485, 15);
            pot4.setFill(Color.BLACK);
            root.getChildren().add(pot4);//we will add an image later

            Circle pot5 = new Circle(350, 485, 15);
            pot5.setFill(Color.BLACK);
            root.getChildren().add(pot5);//we will add an image later

            Circle pot6 = new Circle(685, 485, 15);
            pot6.setFill(Color.BLACK);
            root.getChildren().add(pot6);//we will add an image later


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

            stick = new Stick(root, 30, 150, 30, 250);
            speedmeter = new Rectangle(500, 100, 10, 15);
            speedmeter.setFill(Color.BLUE);
            root.getChildren().add(speedmeter);
            stick.isReadtoStrike = true;
            //setBalls(root);
            //QueBall = new Ball(root, "Queball",16,130,325,12.5,0);
            //Ball(Group root,String name,int ballNo, double x, double y, double radius, Paint fill, double velocity)

            setBalls(root);

            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    updateGameState(now);
                }

            }.start();


            primaryStage.show();
        }

        public Speed1 move() {
        /*
         * write your code here.
         */

            System.out.println("Please enter speed: ");
            Scanner scanner = new Scanner(System.in);
            double vel = scanner.nextDouble();
            System.out.println("Please enter angle: ");
            double angle = scanner.nextDouble();

            return new Speed1(vel,angle);
        }

        public void performMove(Object data) {
        /*
         * write your code here.
         */
            System.out.println((Speed1) data);

        }

}

