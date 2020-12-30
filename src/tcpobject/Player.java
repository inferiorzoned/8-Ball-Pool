package tcpobject;

import java.io.Serializable;

public class Player implements Serializable{
    String name;
    boolean myturn;

    public Player(String name, boolean myturn) {
        this.name = name;
        this.myturn = myturn;
    }

    public void setMyturn(boolean myturn) {
        this.myturn = myturn;
    }

    public boolean isMyturn() {

        return myturn;
    }
}
