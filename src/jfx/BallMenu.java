package jfx;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BallMenu extends Application {

    @Override public void start(Stage stage) {
        // load the image
        Image image = new Image("Ball1.png");

        // simple displays ImageView the image as is
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        iv1.setFitWidth(25);
        iv1.setPreserveRatio(true);

        // resizes the image to have width of 100 while preserving the ratio and using
        // higher quality filtering method; this ImageView is also cached to
        // improve performance
        ImageView iv2 = new ImageView();
        iv2.setImage(image);
        iv2.setFitWidth(100);
        System.out.println(iv2.getFitWidth());
        iv2.setPreserveRatio(true);
        //iv2.setSmooth(true);
        //iv2.setCache(true);

        // defines a viewport into the source image (achieving a "zoom" effect) and
        // displays it rotated
        ImageView iv3 = new ImageView();
        iv3.setImage(image);
        //Rectangle2D viewportRect = new Rectangle2D(400, 300, image.getWidth(), image.getHeight());
        //iv3.setViewport(viewportRect);
        iv3.setLayoutX(300);
        iv3.setLayoutY(200);
        System.out.println("the x is :"+iv3.getX());
        //iv3.setRotate(90);

        Group root = new Group();
        Scene scene = new Scene(root,700,550);
        scene.setFill(Color.WHITE);
        HBox box = new HBox();
        box.getChildren().add(iv1);
        box.getChildren().add(iv2);
        root.getChildren().add(iv3);
        root.getChildren().add(box);

        for(int i = 0;i < 500;i++ ){
            iv3.setLayoutX(iv3.getLayoutX()+.01);
            iv3.setLayoutY(iv3.getLayoutY()+.01);
            System.out.println("iv3 er x :"+ iv3.getLayoutX());
            System.out.println("iv3 er y :"+ iv3.getLayoutY());
        }

        stage.setTitle("ImageView");
        stage.setWidth(415);
        stage.setHeight(200);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}