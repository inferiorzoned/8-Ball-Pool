package jfx;

public class Player {
    String name;
    String ballType;
    boolean playingMode;

    public void checkingFoul(String type){
        if(!ballType.equals(type)){
            //here oi shot e jotogula ball porse oigula check kore dekhte hobe kono ekta type er shathe mile kina
        }
    }

    public String getBallType() {
        return ballType;
    }

    public void setBallType(String ballType) {
        this.ballType = ballType;
    }

    public void setPlayingMode(boolean playingMode) {
        this.playingMode = playingMode;
    }

    public boolean getPlayingMode() {
        return playingMode;
    }

    public Player(String name, String ballType, boolean playingMode) {
        this.name = name;
        this.ballType = ballType;
        this.playingMode = playingMode;
    }
}
