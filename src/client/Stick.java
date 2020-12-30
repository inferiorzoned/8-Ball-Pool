package client;

import javafx.scene.Group;
import javafx.scene.shape.Line;


public class Stick extends Line {
    boolean isReadtoStrike;

    Stick(Group root, double a, double b, double c, double d) {
        super(a,b,c,d);
        this.setStrokeWidth(5.0);
        //this.setRotate(-112.5);
        isReadtoStrike = false;
        root.getChildren().add(this);
    }
}
