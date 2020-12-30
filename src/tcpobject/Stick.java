package tcpobject;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;


public class Stick extends Line {
    boolean isReadtoStrike;

    Stick(Group root, double a, double b,double c,double d) {
        super(a,b,c,d);
        //this.setRotate(-112.5);
        this.setStrokeWidth(6);
        isReadtoStrike = false;
        root.getChildren().add(this);
    }
}
