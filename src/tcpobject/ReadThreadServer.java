package tcpobject;

import jfx.Speed;
import util.NetworkUtil;

public class ReadThreadServer implements Runnable {
    String name;
    NetworkUtil nc1;
    NetworkUtil nc2;
    Thread t;
    boolean pl1Playing,pl2Playing;

    public ReadThreadServer(NetworkUtil nc1, NetworkUtil nc2, String name) {
        this.nc1 = nc1;
        this.nc2 = nc2;
        this.t = new Thread(this);
        this.pl1Playing = true;
        this.pl2Playing = false;
        this.name = name;
        t.start();
    }

    @Override
    public void run() {
        try {
            Speed s;
            Object o;
            while (true) {
                {
                    while (pl1Playing){
                        o = nc1.read();
                        if (o != null) {
                            if (o instanceof Speed) {
                                s = (Speed) o;
                                System.out.println("speed paise " + this.name + " " + s.getVelocity() + " " + s.getAngle());
                                nc2.refresh();
                                nc2.write(s);
                                if(s.getVelocity() == 0){
                                    pl1Playing = false;
                                    pl2Playing = true;
                                }
                            }
                        }
                    }

                    while (pl2Playing){
                        o = nc2.read();
                        if (o != null) {
                            if (o instanceof Speed) {
                                s = (Speed) o;
                                System.out.println("speed paise " + this.name + " " + s.getVelocity() + " " + s.getAngle());
                                nc1.refresh();
                                nc1.write(s);
                                if(s.getVelocity() == 0){
                                    pl2Playing = false;
                                    pl1Playing = true;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
